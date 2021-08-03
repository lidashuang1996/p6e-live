package club.p6e.live.room.platform.bilibili;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Message extends HashMap<String, Object> {

    /** 保存数据的名称 */
    private static final String DATA = "data";
    /** 类型名称 */
    private static final String TYPE = "__TYPE__";
    /** 类型名称 */
    private static final String SPARE = "__SPARE__";
    /** 类型名称 */
    private static final String AGREEMENT = "__AGREEMENT__";
    /** 类型名称 */
    private static final String SOURCE = "__SOURCE__";
    /** 长度名称 */
    private static final String LENGTH = "__LENGTH__";

    /**
     * 序列化得到B站消息对象
     * @param bytes 字节码内容
     * @return 斗鱼消息对象
     */
    public static Message deserializationBytesToMessage(int type, int agreement, int length, int spare, byte[] bytes) {
        return deserializationStringToMessage(type, agreement, length, spare, new String(bytes, StandardCharsets.UTF_8));
    }

    /**
     * 序列化得到B站消息对象
     * @param text 字节码内容
     * @return 斗鱼消息对象
     */
    public static Message deserializationStringToMessage(int type, int agreement, int length, int spare, String text) {
        final Message message = new Message();
        message.put(TYPE, type);
        message.put(SPARE, spare);
        message.put(SOURCE, text);
        message.put(LENGTH, length);
        message.put(AGREEMENT, agreement);
        message.put(DATA, deserialization(text));
        return message;
    }

    /**
     * 反序列化处理
     * @param text 内容
     * @return 反序列化的内容
     */
    private static Object deserialization(String text) {
        boolean isList = false;
        final List<Object> list = new ArrayList<>();
        final Map<String, Object> map = new HashMap<>(16);
        return isList ? list : map;
    }

    /**
     * 创建斗鱼消息对象
     * @return 斗鱼消息对象
     */
    public static Message create(Object data) {
        return null;
    }

    /**
     * 私有化
     * 创建对象只能通过反序列化或者创建对象
     */
    private Message() { }

}
