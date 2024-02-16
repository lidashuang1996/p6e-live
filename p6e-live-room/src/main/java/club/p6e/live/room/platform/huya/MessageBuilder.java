package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomMessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Properties;

/**
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 * <p>
 * 虎牙消息对象建造解构器
 *
 * @author lidashuang
 * @version 1.0
 */
public class MessageBuilder extends LiveRoomMessageBuilder<Message> {

    @SuppressWarnings("ALL")
    @Override
    public synchronized Message deserialization(byte[] bytes) {
        ByteBuf byteBuf = null;
        try {
            final Message message = new Message();
            byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            message.setData(new Taf.Decoder(byteBuf).execute());
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (byteBuf != null) {
                byteBuf.release();
            }
        }
    }


    @SuppressWarnings("ALL")
    @Override
    public synchronized byte[] serialization(Message message) {
        try {
            if (message != null
                    && message.data() != null
                    && message.data() instanceof Properties) {
                final Object mb = message.get("$IS_LENGTH_MARK");
                return new Taf.Encoder(
                        message.data(),
                        ((mb != null && mb instanceof Boolean) ? ((Boolean) mb) : true),
                        true
                ).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
