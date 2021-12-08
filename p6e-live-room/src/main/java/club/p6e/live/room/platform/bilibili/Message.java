package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomMessage;

/**
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

    public void setType(int type) {
        this.put(TYPE_KEY, type);
    }

    public void setSpare(int spare) {
        this.put(SPARE_KEY, spare);
    }

    public void setLength(int length) {
        this.put(LENGTH_KEY, length);
    }

    public void setAgreement(int agreement) {
        this.put(AGREEMENT_KEY, agreement);
    }


    public Integer type() {
        final Object o = this.get(TYPE_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer spare() {
        final Object o = this.get(SPARE_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer length() {
        final Object o = this.get(LENGTH_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer agreement() {
        final Object o = this.get(AGREEMENT_KEY);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }
}
