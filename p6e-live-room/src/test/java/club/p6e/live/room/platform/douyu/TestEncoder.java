package club.p6e.live.room.platform.douyu;

import io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */

public class TestEncoder {

    @Test
    public void test() {
        final Map<String, String> loginMessage = new HashMap<>(2);
        loginMessage.put("roomid", "123456");
        loginMessage.put("type", "loginreq");
        final ByteBuf buf = new Encoder().encode(Message.create(loginMessage));
        final byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println(new String(bytes));
        System.out.println(Arrays.toString(bytes));
    }

}
