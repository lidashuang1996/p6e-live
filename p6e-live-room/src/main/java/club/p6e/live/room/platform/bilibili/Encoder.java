package club.p6e.live.room.platform.bilibili;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼消息编码器
 * 将管道里面的斗鱼消息对象 (club.p6e.live.room.platform.douyu.Message) 编码输出
 *
 * @author lidashuang
 * @version 1.0
 */
public class Encoder {

    /** 平台名称 */
    private static final String PLATFORM = "DOU_YU";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);


    /** 结尾标记符 */
    private static final int ENDING_MARK = 0;
    /** 结尾标记符长度 */
    private static final int ENDING_MARK_LENGTH = 1;
    /** 房间发送给斗鱼消息的类型 */
    private static final int ROOM_SEND_MESSAGE_TYPE = 689;
    /** 斗鱼类型的长度在头部所占字节码长度 */
    private static final int TYPE_LENGTH_HEADER_BYTE_LENGTH = 4;
    /** 斗鱼内容的长度在头部所占字节码长度 */
    private static final int CONTENT_LENGTH_HEADER_BYTE_LENGTH = 4;

    /**
     * 编码操作
     * 编码操作将消息对象转换为 ByteBuf 对象
     *
     * 斗鱼发送消息
     * 消息类型 689 客户端发送给斗鱼服务器
     *
     * 斗鱼消息 = 消息头部
     *          (
     *              消息总长度 [ 小端模式转换 4 ( 这个不算总长度中 )]
     *              消息总长度 [ 小端模式转换 4 ]
     *              消息类型 [ 小端模式转换 4 ]
     *         )
     *         + 消息内容
     *         + 结束符号 (0)
     * @param message 消息对象
     * @return ByteBuf 对象
     */
    public ByteBuf encode(Message message) {
        LOGGER.debug("[ " + PLATFORM + " ] encode message ==> " + message);
        try {
            final ByteBuf byteBuf = Unpooled.buffer(CONTENT_LENGTH_HEADER_BYTE_LENGTH);
            byteBuf.writeByte(ENDING_MARK);
            return byteBuf;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ " + PLATFORM + " ] encode error ==> " + e.getMessage());
            return null;
        }
    }
}
