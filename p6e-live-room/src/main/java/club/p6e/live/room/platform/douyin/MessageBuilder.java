package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomMessageBuilder;

/**
 * @author lidashuang
 * @version 1.0
 */
public class MessageBuilder extends LiveRoomMessageBuilder<Message> {

    @Override
    public Message deserialization(byte[] bytes) {
        return null;
    }

    @Override
    public byte[] serialization(Message message) {
        return new byte[0];
    }
}
