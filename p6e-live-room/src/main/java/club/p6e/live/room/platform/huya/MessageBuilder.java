package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomMessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class MessageBuilder extends LiveRoomMessageBuilder<Message> {

    /** 写入长度的类型 [ 判断是否写入长度信息 ] */
    private static final int WRITE_LENGTH_TYPE = 3;

    /** TAF 解码对象 */
    private final Taf.Decode TAF_DECODE = new Taf.Decode();
    /** TAF 编码对象 */
    private final Taf.Encode TAF_ENCODE = new Taf.Encode();

    @Override
    public synchronized Message deserialization(byte[] bytes) {
        ByteBuf byteBuf = null;
        try {
            byteBuf = Unpooled.buffer(bytes.length);
            final Map<Integer, Object> map = TAF_DECODE.execute(byteBuf);
            final Message message = new Message();
            message.setData(map);
            return message;
        } finally {
            if (byteBuf != null) {
                byteBuf.release();
            }
        }
    }

    @Override
    @SuppressWarnings("all")
    public synchronized byte[] serialization(Message message) {
        ByteBuf byteBuf = null;
        try {
            if (message != null && message.data() != null && message.data() instanceof List) {
                final List<Object> data = (List<Object>) message.data();
                final int type = (int) data.get(0);
                byteBuf = TAF_ENCODE.execute(data, type == WRITE_LENGTH_TYPE);
                final byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                return bytes;
            } else {
                return new byte[0];
            }
        } finally {
            if (byteBuf != null) {
                byteBuf.release();
            }
        }
    }

}
