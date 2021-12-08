//package club.p6e.live.room.platform.bilibili;
//
//import club.p6e.live.room.utils.Utils;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public class Message extends HashMap<String, Object> {
//
//    /** 保存数据的名称 */
//    private static final String DATA = "data";
//    /** 类型名称 */
//    private static final String TYPE = "__TYPE__";
//    /** 类型名称 */
//    private static final String SPARE = "__SPARE__";
//    /** 类型名称 */
//    private static final String AGREEMENT = "__AGREEMENT__";
//    /** 类型名称 */
//    private static final String SOURCE = "__SOURCE__";
//    /** 长度名称 */
//    private static final String LENGTH = "__LENGTH__";
//
//    /**
//     * 头部内容长度
//     */
//    private static final int HEADER_CONTENT_LENGTH = 16;
//
//    /**
//     * 序列化得到B站消息对象
//     * @param bytes 字节码内容
//     * @return 斗鱼消息对象
//     */
//    public static Message deserializationBytesToMessage(int type, int agreement, int length, int spare, byte[] bytes) {
//        return deserializationObjectToMessage(type, agreement, length, spare, new String(bytes, StandardCharsets.UTF_8), true);
//    }
//
//    /**
//     * 序列化得到B站消息对象
//     * @param content 内容
//     * @return 斗鱼消息对象
//     */
//    public static Message deserializationObjectToMessage(int type, int agreement, int length, int spare, Object content, boolean isSerialize) {
//        final Message message = new Message();
//        message.put(TYPE, type);
//        message.put(SPARE, spare);
//        message.put(LENGTH, length);
//        message.put(SOURCE, content);
//        message.put(AGREEMENT, agreement);
//        message.put(DATA, isSerialize && content instanceof String ? deserialization((String) content) : content);
//        return message;
//    }
//
//    /**
//     * 反序列化处理
//     * @param data 内容
//     * @return 反序列化的内容
//     */
//    private static Object deserialization(String data) {
//        return Utils.fromJson(data, Object.class);
//    }
//
//    /**
//     * 创建斗鱼消息对象
//     * @return 斗鱼消息对象
//     */
//    public static Message create(Object data, int type) {
//        final Message message = new Message();
//        final String source = message.serialization(data);
//        message.put(DATA, data);
//        message.setType(type);
//        message.setSpare(1);
//        message.setAgreement(1);
//        message.setSource(source);
//        message.setLength(source.getBytes(StandardCharsets.UTF_8).length + HEADER_CONTENT_LENGTH);
//        return message;
//    }
//
//    /**
//     * 私有化
//     * 创建对象只能通过反序列化或者创建对象
//     */
//    private Message() { }
//
//    public String serialization(Object data) {
//        return data instanceof String ? (String) data : Utils.toJson(data);
//    }
//
//    public Message setType(int type) {
//        this.put(TYPE, type);
//        return this;
//    }
//
//    public Message setSpare(int spare) {
//        this.put(SPARE, spare);
//        return this;
//    }
//
//    public Message setLength(int length) {
//        this.put(LENGTH, length);
//        return this;
//    }
//
//    public Message setAgreement(int agreement) {
//        this.put(AGREEMENT, agreement);
//        return this;
//    }
//
//    private Message setSource(String source) {
//        this.put(SOURCE, source);
//        return this;
//    }
//
//    public Integer type() {
//        final Object o = this.get(TYPE);
//        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
//    }
//
//    public Integer spare() {
//        final Object o = this.get(SPARE);
//        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
//    }
//
//    public Integer length() {
//        final Object o = this.get(LENGTH);
//        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
//    }
//
//    public Integer agreement() {
//        final Object o = this.get(AGREEMENT);
//        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
//    }
//
//    public String source() {
//        final Object o = this.get(SOURCE);
//        return o == null ? null : String.valueOf(o);
//    }
//
//    public Object data() {
//        return this.get(DATA);
//    }
//}
