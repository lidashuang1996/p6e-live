package club.p6e.live.room.platform.huya;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Message extends HashMap<Integer, Object> {

    private static final int SOURCE = -1;

    public static Message create(List<Object> list) {
        final Message message = new Message();
        for (int i = 0; i < list.size(); i++) {
            message.put(i, list.get(i));
        }
        message.put(SOURCE, list);
        return message;
    }

    public static Message deserializationMapToMessage(Map<Integer, Object> map) {
        final Message message = new Message();
        for (final Integer key : map.keySet()) {
            message.put(key, map.get(key));
        }
        return message;
    }

    private Message() {}

    @SuppressWarnings("all")
    public List<Object> source() {
        return (List<Object>) this.get(SOURCE);
    }
    
}
