package club.p6e.live.room;


/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomMessageBuilder<T extends LiveRoomMessage> {

    /** 符号 */
    protected static final String SYMBOL_$ = "$";

    public abstract T deserialization(byte[] bytes);

    public abstract byte[] serialization(T message);

}
