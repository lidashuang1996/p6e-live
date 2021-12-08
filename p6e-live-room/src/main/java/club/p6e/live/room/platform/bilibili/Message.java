package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomMessage;
import club.p6e.live.room.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Message extends LiveRoomMessage {
    public Message setType(int type) {
        this.put(TYPE, type);
        return this;
    }

    public Message setSpare(int spare) {
        this.put(SPARE, spare);
        return this;
    }

    public Message setLength(int length) {
        this.put(LENGTH, length);
        return this;
    }

    public Message setAgreement(int agreement) {
        this.put(AGREEMENT, agreement);
        return this;
    }

    private Message setSource(String source) {
        this.put(SOURCE, source);
        return this;
    }

    public Integer type() {
        final Object o = this.get(TYPE);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer spare() {
        final Object o = this.get(SPARE);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer length() {
        final Object o = this.get(LENGTH);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public Integer agreement() {
        final Object o = this.get(AGREEMENT);
        return o == null ? null : Double.valueOf(String.valueOf(o)).intValue();
    }

    public String source() {
        final Object o = this.get(SOURCE);
        return o == null ? null : String.valueOf(o);
    }

    public Object data() {
        return this.get(DATA);
    }
}
