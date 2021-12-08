package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomMessage;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼消息对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Message extends LiveRoomMessage {

    /** 类型名称 */
    private static final String TYPE_KEY = SYMBOL_$ + "TYPE";
    /** 长度名称 */
    private static final String LENGTH_KEY = SYMBOL_$ + "LENGTH";

    /**
     * 获取类型
     * @return 类型
     */
    public Integer type() {
        final Object o = this.get(TYPE_KEY);
        return o == null ? null : Integer.parseInt(String.valueOf(o));
    }

    /**
     * 写入类型
     * @param type 类型
     */
    public void setType(Integer type) {
        this.put(TYPE_KEY, type);
    }

    /**
     * 获取长度
     * @return 长度
     */
    public Integer length() {
        final Object o = this.get(LENGTH_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    /**
     * 写入长度
     * @param length 长度
     */
    public void setLength(Integer length) {
        this.put(LENGTH_KEY, length);
    }
}