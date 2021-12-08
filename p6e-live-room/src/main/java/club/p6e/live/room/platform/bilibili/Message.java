package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomMessage;

/**
 * B站: https://live.bilibili.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * B站消息对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Message extends LiveRoomMessage {

    /** 类型名称 */
    private static final String TYPE_KEY = SYMBOL_$ + "TYPE";
    /** 暂无名称 */
    private static final String SPARE_KEY = SYMBOL_$ + "SPARE";
    /** 长度名称 */
    private static final String LENGTH_KEY = SYMBOL_$ + "LENGTH";
    /** 协议名称 */
    private static final String AGREEMENT_KEY = SYMBOL_$ + "AGREEMENT";

    /**
     * 写入类型
     * @param type 类型
     */
    public void setType(int type) {
        this.put(TYPE_KEY, type);
    }

    /**
     * 写入扩展
     * @param spare 扩展
     */
    public void setSpare(int spare) {
        this.put(SPARE_KEY, spare);
    }

    /**
     * 写入长度
     * @param length 长度
     */
    public void setLength(int length) {
        this.put(LENGTH_KEY, length);
    }

    /**
     * 写入协议
     * @param agreement 协议
     */
    public void setAgreement(int agreement) {
        this.put(AGREEMENT_KEY, agreement);
    }

    /**
     * 读取类型
     * @return 类型
     */
    public Integer type() {
        final Object o = this.get(TYPE_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    /**
     * 读取扩展
     * @return 扩展
     */
    public Integer spare() {
        final Object o = this.get(SPARE_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    /**
     * 读取长度
     * @return 长度
     */
    public Integer length() {
        final Object o = this.get(LENGTH_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    /**
     * 读取协议
     * @return 协议
     */
    public Integer agreement() {
        final Object o = this.get(AGREEMENT_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }
}
