package club.p6e.live.room;

import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomCodec<T extends LiveRoomMessage> {

    protected LiveRoomMessageBuilder<T> builder;
    public LiveRoomCodec(LiveRoomMessageBuilder<T> builder) {
        this.builder = builder;
    }

    /**
     * 解码
     * @param in
     * @return
     */
    public abstract List<T> decode(ByteBuf byteBuf);

    /**
     * 编码
     * @param message
     * @return
     */
    public abstract ByteBuf encode(T message);

}
