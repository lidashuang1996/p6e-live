package club.p6e.live.room.platform.look;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    /** 编码器 */
    private final Encoder encoder;
    /** WebSocket 客户端对象 */
    private final club.p6e.websocket.client.Client client;

    /**
     * 构造方法初始化
     * @param client 客户端对象
     * @param encoder 编码器对象
     */
    public Client(club.p6e.websocket.client.Client client, Encoder encoder) {
        this.client = client;
        this.encoder = encoder;
    }

    public void sendInitMessage(String[] init) {
        for (final String m : init) {
            client.sendMessageText(m);
        }
    }
    /**
     * 发送心跳消息
     */
    public void sendPantMessage() {
        client.sendMessageText(encoder.encode(Message.create("2", "")));
    }
}
