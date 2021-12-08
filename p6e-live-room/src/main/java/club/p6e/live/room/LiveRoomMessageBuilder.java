package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Message;

import java.util.HashMap;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomMessageBuilder {

    public abstract Message deserialization(byte[] contentBytes);

    public abstract byte[] serialization(Message message);

}
