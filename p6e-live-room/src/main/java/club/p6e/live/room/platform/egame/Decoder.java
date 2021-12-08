//package club.p6e.live.room.platform.egame;
//
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 斗鱼: https://www.douyu.com/
// * 开源项目地址: http://live.p6e.club/
// * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
// *
// * 斗鱼消息解码器
// * 将管道里面字节码解码为斗鱼消息对象 (club.p6e.live.room.platform.douyu.Message)
// *
// * @author lidashuang
// * @version 1.0
// */
//public class Decoder {
//
//    /** 平台名称 */
//    private static final String PLATFORM = "E_GAME";
//    /** 注入日志对象 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);
//
//
//    /** 斗鱼头部的长度 */
//    private static final int HEADER_LENGTH = 8;
//    /** 斗鱼推送给房间消息的类型 */
//    private static final int PUSH_ROOM_MESSAGE_TYPE = 690;
//    /** 斗鱼内容的长度在头部所占字节码长度 */
//    private static final int CONTENT_LENGTH_HEADER_BYTE_LENGTH = 4;
//
//    public List<Message> decode(ByteBuf in) throws Exception {
//        // 解码的内容长度
//        LOGGER.debug("[ " + PLATFORM + " ] decode content length ==> " + in.readableBytes());
//        // 解码且将返回的对象添加到管道中
//        return new ArrayList<>(decodeByteBufToMessages(in));
//    }
//
//    /**
//     * 解码操作
//     * 解码操作将 ByteBuf 转换为消息对象
//     *
//     * 斗鱼接收消息
//     * 消息类型 690 斗鱼服务器推送给客户端的类型
//     *
//     * 斗鱼消息 = 消息头部
//     *          (
//     *              消息总长度 [ 小端模式转换 （4字节） ( 这个不算总长度中 )]
//     *              消息总长度 [ 小端模式转换 （4字节） ]
//     *              消息类型 [ 小端模式转换 （4字节）]
//     *          )
//     *         + 消息内容
//     *
//     * @param buf ByteBuf 对象
//     * @return 转换后的消息对象集合
//     * @throws IOException 转换过成中出现的解析消息异常
//     */
//    private List<Message> decodeByteBufToMessages(ByteBuf buf) throws IOException {
//        // 消息对象的集合
//        final List<Message> result = new ArrayList<>();
//        while (buf.isReadable()) {
//            if (buf.readableBytes() > CONTENT_LENGTH_HEADER_BYTE_LENGTH) {
//                // 消息总长度
//                final int len1 = buf.readIntLE();
//                if (len1 > HEADER_LENGTH && buf.readableBytes() >= len1) {
//                    // 消息总长度
//                    final int len2 = buf.readIntLE();
//                    // 消息类型
//                    final int type = buf.readIntLE();
//                    // 内容数据
//                    final byte[] contentBytes = new byte[len1 - HEADER_LENGTH];
//                    buf.readBytes(contentBytes);
//                    // 核对一下数据
//                    if (len1 == len2 && type == PUSH_ROOM_MESSAGE_TYPE) {
//                        result.add(Message.deserializationBytesToMessage(contentBytes).setType(type).setLength(len1));
//                    } else {
//                        LOGGER.error("[ " + PLATFORM + " ] " +
//                                "check data abnormal, entire message is discarded !!");
//                        throw new IOException("[ " + PLATFORM + " ] " +
//                                "check data abnormal, entire message is discarded !!");
//                    }
//                } else {
//                    LOGGER.error("[ " + PLATFORM + " ] " +
//                            "unread length is less than content length, entire message is discarded !!");
//                    throw new IOException("[ " + PLATFORM + " ] " +
//                            "unread length is less than content length, entire message is discarded !!");
//                }
//            } else {
//                LOGGER.error("[ " + PLATFORM + " ] unread data content length is " +
//                        "less than the header bytecode content length, entire message is discarded !!");
//                throw new IOException("[ " + PLATFORM + " ] unread data content length is " +
//                        "less than the header bytecode content length, entire message is discarded !!");
//            }
//        }
//        return result;
//    }
//
//}
