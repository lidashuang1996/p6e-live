package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomMessageBuilder;
import club.p6e.live.room.utils.Utils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * B站: https://live.bilibili.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * B站消息建造解构器
 *
 * @author lidashuang
 * @version 1.0
 */
public class MessageBuilder extends LiveRoomMessageBuilder<Message> {

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
            if (message.data() != null && message.data() instanceof String) {
                return ((String) message.data()).getBytes(StandardCharsets.UTF_8);
            }
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
     * 反序列化处理
     * @param data 内容
     * @return 反序列化的内容
     */
    public static Object deserializationStringToMessage(String data) {
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
