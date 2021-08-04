package club.p6e.live.room.platform.douyu;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼消息对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Message extends HashMap<String, Object> {

    /** 类型名称 */
    private static final String TYPE = "__TYPE__";
    /** 类型名称 */
    private static final String SOURCE = "__SOURCE__";
    /** 长度名称 */
    private static final String LENGTH = "__LENGTH__";
    /** 保存数据的名称 */
    private static final String DATA = "data";

    /** 符号 */
    private static final String SYMBOL_A = "@A";
    private static final String SYMBOL_S = "@S";
    private static final String SYMBOL_MARK = "@";
    private static final String SYMBOL_SLASH = "/";
    private static final String SYMBOL_EQUAL = "@=";

    /**
     * 反序列化处理
     * @param text 内容
     * @return 反序列化的内容
     */
    private static Object deserialization(String text) {
        boolean isList = false;
        final List<Object> list = new ArrayList<>();
        final Map<String, Object> map = new HashMap<>(16);
        final String[] ts = text.trim().split(SYMBOL_SLASH);
        for (final String t : ts) {
            final String[] is = t.split(SYMBOL_EQUAL);
            if (is.length == 1) {
                // 如果 key-value 里面的 value 没有内容，那么对 key 进行转码操作（可能是 list 格式的数据）
                if (is[0].contains(SYMBOL_SLASH) || is[0].contains(SYMBOL_S) || is[0].contains(SYMBOL_A)) {
                    // 是 list 格式的数据
                    isList = true;
                    is[0] = is[0].replaceAll(SYMBOL_S, SYMBOL_SLASH).replaceAll(SYMBOL_A, SYMBOL_MARK);
                    list.add(deserialization(is[0]));
                } else {
                    // 不是 list 格式的数据
                    isList = false;
                    map.put(is[0], "");
                }
            } else {
                // 如果 key-value 里面的 value 有内容，那么进行转码操作
                is[1] = is[1].replaceAll(SYMBOL_S, SYMBOL_SLASH).replaceAll(SYMBOL_A, SYMBOL_MARK);
                if (is[1].contains(SYMBOL_EQUAL) || is[1].contains(SYMBOL_A) || is[1].contains(SYMBOL_S)) {
                    // 转译后如果还能继续转译，则继续进行操作
                    map.put(is[0], deserialization(is[1]));
                } else {
                    // 不能继续转译，保存起来
                    map.put(is[0], is[1]);
                }
            }
        }
        return isList ? list : map;
    }

    /**
     * 创建斗鱼消息对象
     * @return 斗鱼消息对象
     */
    public static Message create(Object data) {
        return create(data, null);
    }

    /**
     * 创建斗鱼消息对象
     * @return 斗鱼消息对象
     */
    public static Message create(Object data, Integer type) {
        final Message message = new Message();
        final String source = message.serialization(data) + "/";
        message.put(DATA, data);
        message.setType(type);
        message.setSource(source);
        message.setLength(source.getBytes(StandardCharsets.UTF_8).length);
        return message;
    }

    /**
     * 序列化得到斗鱼消息对象
     * @param bytes 字节码内容
     * @return 斗鱼消息对象
     */
    public static Message deserializationBytesToMessage(byte[] bytes) {
        return deserializationStringToMessage(new String(bytes, StandardCharsets.UTF_8));
    }

    /**
     * 序列化得到斗鱼消息对象
     * @param text 字节码内容
     * @return 斗鱼消息对象
     */
    public static Message deserializationStringToMessage(String text) {
        final Message message = new Message();
        message.put(SOURCE, text);
        message.put(DATA, deserialization(text));
        return message;
    }

    /**
     * 私有化
     * 创建对象只能通过反序列化或者创建对象
     */
    private Message() { }

    /**
     * 序列化对象
     * @param data 参数对象
     * @return 序列化后的内容
     */
    @SuppressWarnings("all")
    public String serialization(Object data) {
        if (data != null) {
            final Class<?> className = data.getClass();
            if (data instanceof Map) { // 是否是 kev-value
                return serializationMap((Map<Object, Object>) data);
            } else if (data instanceof List) { // 是否是集合
                return serializationList((List<Object>) data);
            } else if (
                    className.equals(Integer.class) ||
                    className.equals(Byte.class) ||
                    className.equals(Long.class) ||
                    className.equals(Double.class) ||
                    className.equals(Float.class) ||
                    className.equals(Character.class) ||
                    className.equals(Short.class) ||
                    className.equals(Boolean.class) ||
                    className.equals(String.class)) { // 是否是基础类型
                return String.valueOf(data);
            } else { // object 类型
                return serializationObject(data);
            }
        }
        return "";
    }

    /**
     * 对序列化的内容进行转义
     * @param content 需要转义的内容
     * @return 转义后的结果内容
     */
    private String serializationTransferredMeaning(String content) {
        return content.replaceAll(SYMBOL_MARK, SYMBOL_A).replaceAll(SYMBOL_SLASH, SYMBOL_S);
    }

    /**
     * 序列化 Map 对象
     * @param data Map 对象
     * @return 序列化后的内容
     */
    private String serializationMap(Map<Object, Object> data) {
        final StringBuilder sb = new StringBuilder();
        if (data != null && data.size() > 0) {
            for (final Object o : data.keySet()) {
                final String key = serializationTransferredMeaning(serialization(o));
                final String value = serializationTransferredMeaning(serialization(data.get(o)));
                sb.append(key).append(SYMBOL_EQUAL).append(value).append(SYMBOL_SLASH);
            }
            return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
        }
        return sb.toString();
    }

    /**
     * 序列化 List 对象
     * @param data List 对象
     * @return 序列化后的内容
     */
    private String serializationList(List<Object> data) {
        final StringBuilder sb = new StringBuilder();
        if (data != null && data.size() > 0) {
            for (final Object o : data) {
                sb.append(serializationTransferredMeaning(serialization(o))).append(SYMBOL_SLASH);
            }
            return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
        }
        return sb.toString();
    }

    /**
     * 序列化非基础对象
     * @param data 非基础对象
     * @return 序列化后的内容
     */
    private String serializationObject(Object data) {
        final StringBuilder sb = new StringBuilder();
        if (data != null) {
            final Class<?> clazz = data.getClass();
            Class<?> clazzSuper = clazz.getSuperclass();
            final List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            while (clazzSuper != Object.class) {
                fields.addAll(Arrays.asList(clazzSuper.getDeclaredFields()));
                clazzSuper = clazzSuper.getSuperclass();
            }
            fields.stream().filter(
                    field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())
            ).forEach(field -> {
                try {
                    field.setAccessible(true);
                    final String fieldName = field.getName();
                    final Object fieldValue = field.get(data);
                    sb.append(fieldName).append(SYMBOL_EQUAL).append(
                            serializationTransferredMeaning(serialization(fieldValue))).append(SYMBOL_SLASH);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
        }
        return sb.toString();
    }

    /**
     * 读取消息的主体内容数据
     * @return 主体内容
     */
    public Object data() {
        return this.get(DATA);
    }

    /**
     * 读取消息的主体内容数据
     */
    private void setSource(String source) {
        this.put(SOURCE, source);
    }

    /**
     * 读取消息的主体内容数据
     * @return 主体内容
     */
    public String source() {
        final Object o = this.get(SOURCE);
        return o == null ? null : String.valueOf(o);
    }

    /**
     * 设置消息的类型数据
     * @param type 类型
     * @return 斗鱼消息对象
     */
    public Message setType(Integer type) {
        this.put(TYPE, type);
        return this;
    }

    /**
     * 设置消息的长度数据
     * @param length 长度
     * @return 斗鱼消息对象
     */
    public Message setLength(int length) {
        this.put(LENGTH, length);
        return this;
    }

    /**
     * 读取消息的类型数据
     * @return 类型
     */
    public Integer type() {
        final Object o = this.get(TYPE);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    /**
     * 读取消息的长度数据
     * @return 长度
     */
    public Integer length() {
        final Object o = this.get(LENGTH);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }
}
