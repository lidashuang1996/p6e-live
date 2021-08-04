package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼消息解码器
 * 将管道里面字节码解码为
 *
 * @author lidashuang
 * @version 1.0
 */
public class Decoder {

    /** 平台名称 */
    private static final String PLATFORM = "BILI_BILI";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);

    private static final int HEADER_LENGTH = 16;
    private static final int HEADER_LENGTH_BYTE_LENGTH = 4;
    /**
     * 解码操作
     * @param in 输入的对象
     * @param out 输出的对象
     * @throws Exception 解码过程出现的异常
     */
    public void decode(ByteBuf in, List<Message> out) throws Exception {
        // 解码的内容长度
        LOGGER.debug("[ " + PLATFORM + " ] decode content length ==> " + in.readableBytes());
        // 解码且将返回的对象添加到管道中
        out.addAll(decodeByteBufToMessages(in));
    }

    /**
     * 解码操作
     * 解码操作将 ByteBuf 转换为消息对象
     *
     * BliBli 接收消息
     * BliBli 消息 = 消息头部
     *              (
     *                  消息总长度 [ 大端模式转换 (4) ]
     *                  消息头部总长度 [ 数据包头部长度，固定为 16 (2) ]
     *                  数据包协议版本 [
     *                      0	数据包有效负载为未压缩的JSON格式数据
     *                      1	客户端心跳包，或服务器心跳回应（带有人气值）
     *                      2	数据包有效负载为通过zlib压缩后的JSON格式数据
     *                      (2)
     *                  ]
     *                  数据包类型 [
     *                      2	客户端	心跳	不发送心跳包，50-60秒后服务器会强制断开连接
     *                      3	服务器	心跳回应	有效负载为直播间人气值
     *                      5	服务器	通知	有效负载为礼物、弹幕、公告等
     *                      7	客户端	认证（加入房间）	客户端成功建立连接后发送的第一个数据包
     *                      8	服务器	认证成功回应	服务器接受认证包后回应的第一个数据包
     *                      (4)
     *                  ]
     *                  备用字段 [ 固定为 0 (4) ]
     *              )
     *         + 消息内容
     *
     * @param buf ByteBuf 对象
     * @return 转换后的消息对象集合
     * @throws IOException 转换过成中出现的解析消息异常
     */
    private List<Message> decodeByteBufToMessages(ByteBuf buf) throws IOException {
        // 消息对象的集合
        final List<Message> result = new ArrayList<>();
        while (buf.isReadable()) {
            if (buf.readableBytes() >= HEADER_LENGTH) {
                // 内容1 部分
                final int len1 = buf.readInt();
                // 内容2 部分
                final int len2 = (buf.readByte() & 0xFF << 8) | (buf.readByte() & 0xFF);
                // 内容3 部分
                final int agreement = (buf.readByte() & 0xFF << 8) | (buf.readByte() & 0xFF);
                // 内容4 部分
                final int type = buf.readInt();
                // 内容5 部分
                final int spare = buf.readInt();

                // 验证是否符合接收消息的格式
                if (len1 > HEADER_LENGTH && len2 == HEADER_LENGTH) {
                    final byte[] bytes =  new byte[len1 - HEADER_LENGTH];
                    buf.readBytes(bytes);
                    if (agreement == 1) {
                        if (bytes.length == 4) {
                            result.add(Message.deserializationObjectToMessage(
                                    type, agreement, len1, spare, Utils.bytesToIntBig(bytes), false));
                        } else {
                            result.add(Message.deserializationBytesToMessage(type, agreement, len1, spare, bytes));
                        }
                    } else if (agreement == 2) {
                        // 发送协议为 2 的类型的数据的时候，可能是多条和并推送过来的
                        // 需要拆开为一条条的数据
                        int zIndex = 0;
                        final byte[] zBytes = Utils.decompressZlib(bytes);
                        while (zBytes.length >= zIndex + HEADER_LENGTH_BYTE_LENGTH) {
                            final int zLen = Utils.bytesToIntBig(Utils.bytesIntercept(zBytes, zIndex, HEADER_LENGTH_BYTE_LENGTH));
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
                                result.add(Message.deserializationObjectToMessage(
                                        mType, mAgreement, mLen1, mSpare, new String(mBytes).substring(HEADER_LENGTH), true));
                            } else {
                                LOGGER.error("[ " + PLATFORM + " ] " +
                                        "unread length is less than content length, entire message is discarded !!");
                                throw new IOException("[ " + PLATFORM + " ] " +
                                        "unread length is less than content length, entire message is discarded !!");
                            }
                        }
                    } else {
                        result.add(Message.deserializationObjectToMessage(type, agreement, len1, spare, bytes, false));
                    }
                } else {
                    LOGGER.error("[ " + PLATFORM + " ] " +
                            "unread length is less than content length, entire message is discarded !!");
                    throw new IOException("[ " + PLATFORM + " ] " +
                            "unread length is less than content length, entire message is discarded !!");
                }
            } else {
                LOGGER.error("[ " + PLATFORM + " ] unread data content length is " +
                        "less than the header bytecode content length, entire message is discarded !!");
                throw new IOException("[ " + PLATFORM + " ] unread data content length is " +
                        "less than the header bytecode content length, entire message is discarded !!");
            }
        }
        return result;
    }

}
