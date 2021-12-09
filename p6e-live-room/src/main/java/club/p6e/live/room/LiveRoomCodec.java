package club.p6e.live.room;

import io.netty.buffer.ByteBuf;
import java.util.List;

/**
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 消息编码解码器
 *
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomCodec<T extends LiveRoomMessage> {

    /**
     * 消息建造解构器
     */
    protected LiveRoomMessageBuilder<T> builder;

    /**
     * 构造方法初始化消息建造解构器
     * @param builder 消息建造解构器
     */
    public LiveRoomCodec(LiveRoomMessageBuilder<T> builder) {
        this.builder = builder;
    }

    /**
     * 解码内容
     * @param byteBuf ByteBuf 字解码对象
     * @return 消息对象
     */
    public abstract List<T> decode(ByteBuf byteBuf);

    /**
     * 编码内容
     * @param message 消息对象
     * @return ByteBuf 字解码对象
     */
    public abstract ByteBuf encode(T message);

}
