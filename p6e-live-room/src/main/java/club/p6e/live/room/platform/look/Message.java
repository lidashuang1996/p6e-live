//package club.p6e.live.room.platform.look;
//
//import club.p6e.live.room.utils.Utils;
//
//import java.util.*;
//
///**
// * 斗鱼: https://www.douyu.com/
// * 开源项目地址: http://live.p6e.club/
// * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
// *
// * 斗鱼消息对象
// *
// * @author lidashuang
// * @version 1.0
// */
//public class Message extends HashMap<String, Object> {
//
//    /** 类型名称 */
//    private static final String TYPE = "__TYPE__";
//    /** 类型名称 */
//    private static final String SOURCE = "__SOURCE__";
//
//    public static Message create(String type, Object source) {
//        if (source == null) {
//            return deserializationStringToMessage(type, "");
//        }
//        else if (source instanceof String && ((String) source).isEmpty()) {
//            return deserializationStringToMessage(type, "");
//        } else {
//            return deserializationStringToMessage(type, serialization(source));
//        }
//    }
//
//    public static String serialization(Object source) {
//        return Utils.toJson(source);
//    }
//
//    /**
//     * 序列化
//     */
//    public static Message deserializationStringToMessage(String type, String source) {
//        final Message message = new Message();
//        message.put(TYPE, type);
//        message.put(SOURCE, source);
//        System.out.println(type + "    " + source);
//        if (!source.isEmpty()) {
//            final Map<String, Object> map = Utils.fromJsonToMap(source, String.class, Object.class);
//            if (map != null && map.size() > 0) {
//                for (final String key : map.keySet()) {
//                    message.put(key, map.get(key));
//                }
//            }
//        }
//        return message;
//    }
//
//    /**
//     * 私有化
//     * 创建对象只能通过反序列化或者创建对象
//     */
//    private Message() { }
//
//    /**
//     * 读取消息的主体内容数据
//     */
//    private void setSource(String source) {
//        this.put(SOURCE, source);
//    }
//
//    /**
//     * 读取消息的主体内容数据
//     * @return 主体内容
//     */
//    public String source() {
//        final Object o = this.get(SOURCE);
//        return o == null ? null : String.valueOf(o);
//    }
//
//    /**
//     * 设置消息的类型数据
//     * @param type 类型
//     * @return 斗鱼消息对象
//     */
//    public Message setType(String type) {
//        this.put(TYPE, type);
//        return this;
//    }
//
//    /**
//     * 读取消息的类型数据
//     * @return 类型
//     */
//    public String type() {
//        final Object o = this.get(TYPE);
//        return o == null ? null : String.valueOf(o);
//    }
//}
