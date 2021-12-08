package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.LiveRoomMessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Codec extends LiveRoomCodec<Message> {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Codec.class);

    /**
     * 构造方法初始化
     * @param builder 消息构建器
     */
    public Codec(LiveRoomMessageBuilder<Message> builder) {
        super(builder);
    }

    @Override
    public List<Message> decode(ByteBuf byteBuf) {
        // 解码的内容长度
        LOGGER.debug("[ HuYa ] decode content length ==> " + byteBuf.readableBytes());
        return decodeByteBufToMessage(byteBuf);
    }

    @Override
    public ByteBuf encode(Message message) {
        LOGGER.debug("[ HuYa ] encode message ==> " + message);
        return encodeMessageToByteBuf(message);
    }

    /**
     * 解码操作
     * @param byteBuf 输入的对象
     * @return 输出的对象
     */
    private List<Message> decodeByteBufToMessage(ByteBuf byteBuf) {
        final List<Message> list = new ArrayList<>();
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        final Message message = this.builder.deserialization(bytes);
        list.add(message);
        return list;
    }

    /**
     * 编码操作
     * 编码操作将消息对象转换为 ByteBuf 对象
     * @param message 消息对象
     * @return ByteBuf 对象
     */
    private ByteBuf encodeMessageToByteBuf(Message message) {
        final byte[] bytes = this.builder.serialization(message);
        final ByteBuf byteBuf = Unpooled.buffer(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}