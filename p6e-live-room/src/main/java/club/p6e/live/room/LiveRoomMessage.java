package club.p6e.live.room;

import java.util.HashMap;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomMessage extends HashMap<String, Object> {

    /** 符号 */
    protected static final String SYMBOL_$ = "$";

    /** 消息内容 */
    private Object data;

    /** 读取消息 */
    public Object data() {
        return data;
    }

    /** 写入消息 */
    public void setData(Object data) {
        this.data = data;
    }

}
