package club.p6e.live.room.platform.douyu;

import java.util.HashMap;
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

    public Client(club.p6e.websocket.client.Client client, Encoder encoder) {
        this.client = client;
        this.encoder = encoder;
    }

    public void sendLoginMessage(String rid) {
        final Map<String, String> loginMessage = new HashMap<>(2);
        loginMessage.put("roomid", rid);
        loginMessage.put("type", "loginreq");
        client.sendMessageBinary(encoder.encode(Message.create(loginMessage)));
    }

    public void sendGroupMessage(String rid) {
        final Map<String, String> groupMessage = new HashMap<>(2);
        groupMessage.put("roomid", rid);
        groupMessage.put("gid", "-9999");
        groupMessage.put("type", "joingroup");
        client.sendMessageBinary(encoder.encode(Message.create(groupMessage)));
    }

    public void sendAllGiftMessage() {
        final Map<String, String> allGiftMessage = new HashMap<>(2);
        allGiftMessage.put("roomid", "");
        allGiftMessage.put("gid", "-9999");
        client.sendMessageBinary(encoder.encode(Message.create(allGiftMessage)));
    }

    public void sendPantMessage() {
        final Map<String, String> pantMessage = new HashMap<>(1);
        pantMessage.put("type", "mrkl");
        client.sendMessageBinary(encoder.encode(Message.create(pantMessage)));
    }
}
