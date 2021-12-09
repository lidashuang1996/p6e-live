package club.p6e.live.room;

import java.util.HashMap;

/**
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 消息模型对象
 *
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
