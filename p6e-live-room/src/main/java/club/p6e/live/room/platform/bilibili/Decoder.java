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
     * 斗鱼接收消息
     * 消息类型 690 斗鱼服务器推送给客户端的类型
     *
     * 斗鱼消息 = 消息头部
     *          (
     *              消息总长度 [ 小端模式转换 （4字节） ( 这个不算总长度中 )]
     *              消息总长度 [ 小端模式转换 （4字节） ]
     *              消息类型 [ 小端模式转换 （4字节）]
     *          )
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
                final int len1 = buf.readIntLE();
                // 内容2 部分
                final int len2 = (buf.readByte() & 0xFF << 8) | (buf.readByte() & 0xFF);
                // 内容3 部分
                final int agreement = (buf.readByte() & 0xFF << 8) | (buf.readByte() & 0xFF);
                // 内容4 部分
                final int type = buf.readIntLE();
                // 内容5 部分
                final int spare = buf.readIntLE();

                // 验证是否符合接收消息的格式
                if (len1 > HEADER_LENGTH && len2 == HEADER_LENGTH) {
                    final byte[] bytes =  new byte[len1 - HEADER_LENGTH];
                    buf.readBytes(bytes);
                    if (agreement == 2) {
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
                            // result.add(new Source(itemBytes, type, spare, new String(itemBytes, StandardCharsets.UTF_8).substring(16)));
                            System.out.println("aaaaa --> " + new String(mBytes));
                        }
                    } else {
                        System.out.println("bbbb --> " + new String(bytes));
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
