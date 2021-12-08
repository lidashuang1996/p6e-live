package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomMessageBuilder;
import club.p6e.live.room.platform.douyu.Message;
import club.p6e.live.room.utils.Utils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class MessageBuilder extends LiveRoomMessageBuilder {

    /** 符号 */
    private static final String SYMBOL_$ = "$";

    @Override
    public Message deserialization(byte[] contentBytes) {
        final Message message = new Message();
        message.setData(deserializationStringToMessage(new String(contentBytes, StandardCharsets.UTF_8)));
        return message;
    }

    @Override
    public byte[] serialization(Message message) {
        if (message == null) {
            return new byte[0];
        } else {
            final Map<String, Object> map = new HashMap<>(message.size());
            for (final String key : message.keySet()) {
                if (!key.startsWith(SYMBOL_$)) {
                    map.put(key, message.get(key));
                }
            }
            return (serializationMessageToString(map)).getBytes(StandardCharsets.UTF_8);
        }
    }


    /**
    * 序列化得到B站消息对象
    * @param content 内容
    * @return 斗鱼消息对象
    */
    public static Message deserializationObjectToMessage(int type, int agreement, int length, int spare, Object content, boolean isSerialize) {
        final Message message = new Message();
        message.put(TYPE, type);
        message.put(SPARE, spare);
        message.put(LENGTH, length);
        message.put(SOURCE, content);
        message.put(AGREEMENT, agreement);
        message.put(DATA, isSerialize && content instanceof String ? deserialization((String) content) : content);
        return message;
    }

    /**
     * 反序列化处理
     * @param data 内容
     * @return 反序列化的内容
     */
    private static Object deserializationStringToMessage(String data) {
        return Utils.fromJson(data, Object.class);
    }

    /**
     * 序列化对象
     * @param data 对象
     * @return 序列化的内容
     */
    public static String serializationMessageToString(Object data) {
        return data instanceof String ? (String) data : Utils.toJson(data);
    }

}
