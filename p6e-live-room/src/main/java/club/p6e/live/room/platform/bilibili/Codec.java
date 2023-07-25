package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.brotli.dec.BrotliInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * B站: https://live.bilibili.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 * <p>
 * B站编解码器
 *
 * @author lidashuang
 * @version 1.0
 */
public class Codec extends LiveRoomCodec<Message> {

    /**
     * 注入日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Codec.class);

    /**
     * 头部长度
     */
    private static final int HEADER_LENGTH = 16;
    /**
     * 长度在头部占的长度
     */
    private static final int LENGTH_HEADER_BYTE_LENGTH = 4;

    /**
     * 空的字节内容
     */
    private static final int EMPTY_BYTE = 0;

    /**
     * 构造方法初始化
     *
     * @param builder 消息构建器
     */
    public Codec(MessageBuilder builder) {
        super(builder);
    }

    @Override
    public List<Message> decode(ByteBuf byteBuf) {
        // 解码的内容长度
        LOGGER.debug("[ BiliBili ] decode content length ==> " + byteBuf.readableBytes());
        // 解码且将返回的对象添加到管道中
        return decodeByteBufToMessages(byteBuf);
    }

    @Override
    public ByteBuf encode(Message message) {
        LOGGER.debug("[ BiliBili ] encode message ==> " + message);
        return encodeMessageToByteBuf(message);
    }

    /**
     * 解码操作
     * 解码操作将 ByteBuf 转换为消息对象
     * <p>
     * BiliBili 接收消息
     * BiliBili 消息 = 消息头部
     * (
     * 消息总长度 [ 大端模式转换 (4) ]
     * 消息头部总长度 [ 数据包头部长度，固定为 16 (2) ]
     * 数据包协议版本 [
     * 0	数据包有效负载为未压缩的JSON格式数据
     * 1	客户端心跳包，或服务器心跳回应（带有人气值）
     * 2	数据包有效负载为通过zlib压缩后的JSON格式数据
     * (2)
     * ]
     * 数据包类型 [
     * 2	客户端	心跳	不发送心跳包，50-60秒后服务器会强制断开连接
     * 3	服务器	心跳回应	有效负载为直播间人气值
     * 5	服务器	通知	有效负载为礼物、弹幕、公告等
     * 7	客户端	认证（加入房间）	客户端成功建立连接后发送的第一个数据包
     * 8	服务器	认证成功回应	服务器接受认证包后回应的第一个数据包
     * (4)
     * ]
     * 备用字段 [ 固定为 0 (4) ]
     * )
     * + 消息内容
     *
     * @param byteBuf ByteBuf 对象
     * @return 转换后的消息对象集合
     */
    private List<Message> decodeByteBufToMessages(ByteBuf byteBuf) {
        // 消息对象的集合
        final List<Message> result = new ArrayList<>();
        try {
            while (byteBuf.isReadable()) {
                if (byteBuf.readableBytes() >= HEADER_LENGTH) {
                    // 内容1 部分
                    final int len1 = byteBuf.readInt();
                    // 内容2 部分
                    final int len2 = (byteBuf.readByte() & 0xFF << 8) | (byteBuf.readByte() & 0xFF);
                    // 内容3 部分
                    final int agreement = (byteBuf.readByte() & 0xFF << 8) | (byteBuf.readByte() & 0xFF);
                    // 内容4 部分
                    final int type = byteBuf.readInt();
                    // 内容5 部分
                    final int spare = byteBuf.readInt();
                    // 验证是否符合接收消息的格式
                    if (len1 > HEADER_LENGTH && len2 == HEADER_LENGTH) {
                        final byte[] bytes = new byte[len1 - HEADER_LENGTH];
                        byteBuf.readBytes(bytes);
                        if (agreement == 0 || agreement == 1) {
                            final Message message = new Message();
                            message.setType(type);
                            message.setSpare(spare);
                            message.setLength(len1);
                            message.setAgreement(agreement);
                            if (bytes.length == 4) {
                                if (byteBuf.readableBytes() == 15) {
                                    final byte[] pantBytes = new byte[15];
                                    byteBuf.readBytes(pantBytes);
                                    message.setData(new String(pantBytes, StandardCharsets.UTF_8));
                                } else {
                                    message.setData(Utils.bytesToIntBig(bytes));
                                }
                            } else {
                                message.setData(new String(bytes, StandardCharsets.UTF_8));
                            }
                            result.add(message);
                        } else if (agreement == 2) {
                            // ======================== 协议2的内容保留 ========================
                            // ======================== 协议2的内容保留 ========================
                            // ======================== 协议2的内容保留 ========================
                            // 发送协议为 2 的类型的数据的时候，可能是多条和并推送过来的
                            // 需要拆开为一条条的数据
                            int zIndex = 0;
                            final byte[] zBytes = Utils.decompressZlib(bytes);
                            while (zBytes.length >= zIndex + LENGTH_HEADER_BYTE_LENGTH) {
                                final int zLen = Utils.bytesToIntBig(Utils.bytesIntercept(zBytes, zIndex, LENGTH_HEADER_BYTE_LENGTH));
                                final byte[] mBytes = Utils.bytesIntercept(zBytes, zIndex, zLen);
                                zIndex += zLen;
                                // 解压消息里面的数据
                                // 内容1 部分
                                final int mLen1 = Utils.bytesToIntBig(Utils.bytesIntercept(mBytes, 0, 4));
                                // 内容2 部分
                                final int mLen2 = (mBytes[4] & 0xFF << 8) | (mBytes[5] & 0xFF);
                                // 内容3 部分
                                final int mAgreement = (mBytes[6] & 0xFF << 8) | (mBytes[7] & 0xFF);
                                // 内容4 部分
                                final int mType = Utils.bytesToIntBig(Utils.bytesIntercept(mBytes, 8, 4));
                                // 内容5 部分
                                final int mSpare = Utils.bytesToIntBig(Utils.bytesIntercept(mBytes, 12, 4));
                                if (mLen1 > HEADER_LENGTH && mLen2 == HEADER_LENGTH) {
                                    final Message message = this.builder.deserialization(Utils.bytesIntercept(mBytes, 16, mBytes.length - 16));
                                    if (message != null) {
                                        message.setType(mType);
                                        message.setSpare(mSpare);
                                        message.setLength(mLen1);
                                        message.setAgreement(mAgreement);
                                        result.add(message);
                                    }
                                } else {
                                    LOGGER.error("[ BiliBili ] " + "unread length is less than content length, entire message is discarded !!");
                                    throw new IOException("[ BiliBili ] " + "unread length is less than content length, entire message is discarded !!");
                                }
                            }
                            // ======================== 协议2的内容保留 ========================
                            // ======================== 协议2的内容保留 ========================
                            // ======================== 协议2的内容保留 ========================
                        } else if (agreement == 3) {
                            // https://github.com/nixxcode/jvm-brotli
                            // google - brotli 压缩算法
                            // Initialize decompressor by binding it to our file input stream
                            byte[] brotliBytes = null;
                            ByteArrayInputStream byteArrayInputStream = null;
                            ByteArrayOutputStream byteArrayOutputStream = null;
                            BrotliInputStream brotliInputStream = null;
                            try {
                                byteArrayInputStream = new ByteArrayInputStream(bytes);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                brotliInputStream = new BrotliInputStream(byteArrayInputStream);
                                int br = brotliInputStream.read();
                                while (br != -1) {
                                    byteArrayOutputStream.write(br);
                                    br = brotliInputStream.read();
                                }
                                brotliBytes = byteArrayOutputStream.toByteArray();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (byteArrayInputStream != null) {
                                        byteArrayInputStream.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (byteArrayOutputStream != null) {
                                        byteArrayOutputStream.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (brotliInputStream != null) {
                                        brotliInputStream.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            // google - brotli 压缩算法
                            // Initialize decompressor by binding it to our file input stream
                            // ---[END]-----
                            if (brotliBytes != null && brotliBytes.length > 0) {
                                final ByteBuf bb = Unpooled.copiedBuffer(brotliBytes);
                                try {
                                    if (bb.readableBytes() > 4) {
                                        while (bb.readableBytes() > 4) {
                                            // 读取长度
                                            final int bbLength = bb.readInt();
                                            bb.readerIndex(bb.readerIndex() - 4);
                                            if (bb.readableBytes() >= bbLength) {
                                                final ByteBuf rbb = bb.readBytes(bbLength);
                                                try {
                                                    result.addAll(decodeByteBufToMessages(rbb));
                                                } finally {
                                                    rbb.release();
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                } finally {
                                    bb.release();
                                }
                            }
                        }
                    } else {
                        LOGGER.error("[ BiliBili ] " + "unread length is less than content length, entire message is discarded !!");
                        throw new IOException("[ BiliBili ] " + "unread length is less than content length, entire message is discarded !!");
                    }
                } else {
                    LOGGER.error("[ BiliBili ] unread data content length is " + "less than the header bytecode content length, entire message is discarded !!");
                    throw new IOException("[ BiliBili ] unread data content length is " + "less than the header bytecode content length, entire message is discarded !!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili ] encode error ==> " + e.getMessage());
        }
        return result;
    }

    /**
     * 编码操作
     * 编码操作将消息对象转换为 ByteBuf 对象
     * <p>
     * BiliBili 发送消息
     * BiliBili 消息 = 消息头部
     * (
     * 消息总长度 [ 大端模式转换 (4) ]
     * 消息头部总长度 [ 数据包头部长度，固定为 16 (2) ]
     * 数据包协议版本 [
     * 0	数据包有效负载为未压缩的JSON格式数据
     * 1	客户端心跳包，或服务器心跳回应（带有人气值）
     * 2	数据包有效负载为通过zlib压缩后的JSON格式数据
     * (2)
     * ]
     * 数据包类型 [
     * 2	客户端	心跳	不发送心跳包，50-60秒后服务器会强制断开连接
     * 3	服务器	心跳回应	有效负载为直播间人气值
     * 5	服务器	通知	有效负载为礼物、弹幕、公告等
     * 7	客户端	认证（加入房间）	客户端成功建立连接后发送的第一个数据包
     * 8	服务器	认证成功回应	服务器接受认证包后回应的第一个数据包
     * (4)
     * ]
     * 备用字段 [ 固定为 1 (4) ]
     * )
     * + 消息内容
     *
     * @param message 消息对象
     * @return ByteBuf 对象
     */
    private ByteBuf encodeMessageToByteBuf(Message message) {
        try {
            final byte[] bytes = this.builder.serialization(message);
            final int length = HEADER_LENGTH + bytes.length;
            final ByteBuf byteBuf = Unpooled.buffer(length);
            byteBuf.writeInt(length);
            byteBuf.writeByte(EMPTY_BYTE);
            byteBuf.writeByte(HEADER_LENGTH);
            byteBuf.writeByte(EMPTY_BYTE);
            byteBuf.writeByte(message.agreement());
            byteBuf.writeInt(message.type());
            byteBuf.writeInt(message.spare());
            byteBuf.writeBytes(bytes);
            return byteBuf;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili ] encode error ==> " + e.getMessage());
            return null;
        }
    }

}
