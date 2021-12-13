package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomMessage;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Message extends LiveRoomMessage {

    /** ID 名称 */
    private static final String ID_KEY = SYMBOL_$ + "ID";
    /** 类型名称 */
    private static final String TYPE_KEY = SYMBOL_$ + "TYPE";
    /** 扩展名称 */
    private static final String EXTEND_KEY = SYMBOL_$ + "EXTEND";

    /**
     * 获取类型
     * @return 类型
     */
    public String id() {
        final Object o = this.get(TYPE_KEY);
        return o == null ? null : String.valueOf(o);
    }

    /**
     * 写入类型
     * @param type 类型
     */
    public void setId(String type) {
        this.put(ID_KEY, type);
    }

    /**
     * 获取类型
     * @return 类型
     */
    public String type() {
        final Object o = this.get(TYPE_KEY);
        return o == null ? null : String.valueOf(o);
    }

    /**
     * 写入类型
     * @param type 类型
     */
    public void setType(String type) {
        this.put(TYPE_KEY, type);
    }

    /**
     * 获取扩展内容
     * @return 扩展
     */
    @SuppressWarnings("all")
    public Map<Integer, Object> extend() {
        final Object o = this.get(EXTEND_KEY);
        return o == null ? null : (Map<Integer, Object>) o;
    }

    /**
     * 写入扩展内容
     * @param extend 扩展
     */
    public void setExtend(Map<Integer, Object> extend) {
        this.put(EXTEND_KEY, extend);
    }
}
