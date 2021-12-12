package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCodec;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Codec extends LiveRoomCodec<Message> {

    public Codec(MessageBuilder builder) {
        super(builder);
    }

    @Override
    public List<Message> decode(ByteBuf byteBuf) {
        return null;
    }

    @Override
    public ByteBuf encode(Message message) {
        return null;
    }
}
