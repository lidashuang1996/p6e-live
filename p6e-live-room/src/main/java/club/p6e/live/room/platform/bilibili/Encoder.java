//package club.p6e.live.room.platform.bilibili;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 斗鱼: https://www.douyu.com/
// * 开源项目地址: http://live.p6e.club/
// * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
// *
// * 斗鱼消息编码器
// * 将管道里面的斗鱼消息对象 (club.p6e.live.room.platform.douyu.Message) 编码输出
// *
// * @author lidashuang
// * @version 1.0
// */
//public class Encoder {
//
//    /** 平台名称 */
//    private static final String PLATFORM = "BILI_BILI";
//    /** 注入日志对象 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);
//
//    /** 空的字节内容 */
//    private static final int EMPTY_BYTE = 0;
//    /** 头部内容长度 */
//    private static final int HEADER_CONTENT_LENGTH = 16;
//
//    /**
//     * 编码操作
//     * 编码操作将消息对象转换为 ByteBuf 对象
//     *
//     * BliBli 发送消息
//     * BliBli 消息 = 消息头部
//     *              (
//     *                  消息总长度 [ 大端模式转换 (4) ]
//     *                  消息头部总长度 [ 数据包头部长度，固定为 16 (2) ]
//     *                  数据包协议版本 [
//     *                      0	数据包有效负载为未压缩的JSON格式数据
//     *                      1	客户端心跳包，或服务器心跳回应（带有人气值）
//     *                      2	数据包有效负载为通过zlib压缩后的JSON格式数据
//     *                      (2)
//     *                  ]
//     *                  数据包类型 [
//     *                      2	客户端	心跳	不发送心跳包，50-60秒后服务器会强制断开连接
//     *                      3	服务器	心跳回应	有效负载为直播间人气值
//     *                      5	服务器	通知	有效负载为礼物、弹幕、公告等
//     *                      7	客户端	认证（加入房间）	客户端成功建立连接后发送的第一个数据包
//     *                      8	服务器	认证成功回应	服务器接受认证包后回应的第一个数据包
//     *                      (4)
//     *                  ]
//     *                  备用字段 [ 固定为 1 (4) ]
//     *              )
//     *         + 消息内容
//     * @param message 消息对象
//     * @return ByteBuf 对象
//     */
//    public ByteBuf encode(Message message) {
//        LOGGER.debug("[ " + PLATFORM + " ] encode message ==> " + message);
//        try {
//            final ByteBuf byteBuf = Unpooled.buffer(message.length());
//            byteBuf.writeInt(message.length());
//            byteBuf.writeByte(EMPTY_BYTE);
//            byteBuf.writeByte(HEADER_CONTENT_LENGTH);
//            byteBuf.writeByte(EMPTY_BYTE);
//            byteBuf.writeByte(message.agreement());
//            byteBuf.writeInt(message.type());
//            byteBuf.writeInt(message.spare());
//            byteBuf.writeBytes(message.source().getBytes(StandardCharsets.UTF_8));
//            return byteBuf;
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("[ " + PLATFORM + " ] encode error ==> " + e.getMessage());
//            return null;
//        }
//    }
//}
