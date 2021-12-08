package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼编解码对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Codec extends LiveRoomCodec<Message> {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Codec.class);

    /** 斗鱼头部的长度 */
    private static final int HEADER_LENGTH = 8;
    /** 斗鱼推送给房间消息的类型 */
    private static final int ROOM_RECEIVE_MESSAGE_TYPE = 690;
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
     * 构造方法初始化
     * @param builder 消息构建器
     */
    public Codec(MessageBuilder builder) {
        super(builder);
    }

    @Override
    public List<Message> decode(ByteBuf byteBuf) {
        // 解码的内容长度
        LOGGER.debug("[ DouYu ] decode content length ==> " + byteBuf.readableBytes());
        return decodeByteBufToMessages(byteBuf);
    }

    @Override
    public ByteBuf encode(Message message) {
        LOGGER.debug("[ DouYu ] encode message ==> " + message);
        return encodeMessageToByteBuf(message);
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
     * @param byteBuf ByteBuf 对象
     * @return 转换后的消息对象集合
     */
    private List<Message> decodeByteBufToMessages(ByteBuf byteBuf) {
        // 消息对象的集合
        final List<Message> result = new ArrayList<>();
        try {
            while (byteBuf.isReadable()) {
                if (byteBuf.readableBytes() > CONTENT_LENGTH_HEADER_BYTE_LENGTH) {
                    // 消息总长度
                    final int len1 = byteBuf.readIntLE();
                    if (len1 > HEADER_LENGTH && byteBuf.readableBytes() >= len1) {
                        // 消息总长度
                        final int len2 = byteBuf.readIntLE();
                        // 消息类型
                        final int type = byteBuf.readIntLE();
                        // 内容数据
                        final byte[] contentBytes = new byte[len1 - HEADER_LENGTH];
                        byteBuf.readBytes(contentBytes);
                        // 核对一下数据
                        if (len1 == len2 && type == ROOM_RECEIVE_MESSAGE_TYPE) {
                            final Message message = this.builder.deserialization(contentBytes);
                            message.setType(type);
                            message.setLength(len1);
                            result.add(message);
                        } else {
                            LOGGER.error("[ DouYu ] " + "check data abnormal, entire message is discarded !!");
                            throw new IOException("[ DouYu ] " + "check data abnormal, entire message is discarded !!");
                        }
                    } else {
                        LOGGER.error("[ DouYu ] " + "unread length is less than content length, entire message is discarded !!");
                        throw new IOException("[ DouYu ] " + "unread length is less than content length, entire message is discarded !!");
                    }
                } else {
                    LOGGER.error("[ DouYu ] unread data content length is " + "less than the header bytecode content length, entire message is discarded !!");
                    throw new IOException("[ DouYu ] unread data content length is " + "less than the header bytecode content length, entire message is discarded !!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu ] decode error ==> " + e.getMessage());
        }
        return result;
    }

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
    public ByteBuf encodeMessageToByteBuf(Message message) {
        try {
            // 序列化消息
            final byte[] contents = this.builder.serialization(message);
            // 读取消息类型
            final int type = message.type() == null ? ROOM_SEND_MESSAGE_TYPE : message.type();
            // 消息长度 = 消息内容长度 + 消息长度在头部的长度 + 结束符号长度
            final int length = contents.length + TYPE_LENGTH_HEADER_BYTE_LENGTH + CONTENT_LENGTH_HEADER_BYTE_LENGTH + ENDING_MARK_LENGTH;
            // 创建指定长度的 ByteBuf
            final ByteBuf byteBuf = Unpooled.buffer(length + CONTENT_LENGTH_HEADER_BYTE_LENGTH);
            // 写入长度1
            byteBuf.writeIntLE(length);
            // 写入长度2
            byteBuf.writeIntLE(length);
            // 写入消息类型
            byteBuf.writeIntLE(type);
            // 写入消息内容
            byteBuf.writeBytes(contents);
            // 写入结束符
            byteBuf.writeByte(ENDING_MARK);
            return byteBuf;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu ] encode error ==> " + e.getMessage());
            return null;
        }
    }

}
