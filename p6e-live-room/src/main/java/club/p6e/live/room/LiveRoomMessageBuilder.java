package club.p6e.live.room;

/**
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 消息建造解构器
 * T 消息对象
 *
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomMessageBuilder<T extends LiveRoomMessage> {

    /** 符号 */
    protected static final String SYMBOL_$ = "$";

    /**
     * 反序列化
     * @param bytes 字解码
     * @return 消息对象
     */
    public abstract T deserialization(byte[] bytes);

    /**
     * 序列化
     * @param message 消息对象
     * @return 字解码
     */
    public abstract byte[] serialization(T message);

}
