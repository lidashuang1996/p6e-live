package club.p6e.live.room.platform.douyu;

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

    /**
     * 发送登录的消息
     * @param rid 房间的编号
     */
    public void sendLoginMessage(String rid) {
        final Map<String, String> loginMessage = new HashMap<>(2);
        loginMessage.put("roomid", rid);
        loginMessage.put("type", "loginreq");
        client.sendMessageBinary(encoder.encode(Message.create(loginMessage)));
    }

    /**
     * 发送加入组的消息
     * @param rid 房间的编号
     */
    public void sendGroupMessage(String rid) {
        final Map<String, String> groupMessage = new HashMap<>(2);
        groupMessage.put("rid", rid);
        groupMessage.put("gid", "-9999");
        groupMessage.put("type", "joingroup");
        client.sendMessageBinary(encoder.encode(Message.create(groupMessage)));
    }

    /**
     * 发送接收全部礼物消息
     */
    public void sendAllGiftMessage() {
        final Map<String, Object> allGiftMessage = new HashMap<>(2);
        allGiftMessage.put("type", "dmfbdreq");
        Map<String, Integer> dfl1 = new HashMap<>(2);
        dfl1.put("ss", 0);
        dfl1.put("sn", 105);
        Map<String, Integer> dfl2 = new HashMap<>(2);
        dfl2.put("ss", 0);
        dfl2.put("sn", 106);
        Map<String, Integer> dfl3 = new HashMap<>(2);
        dfl3.put("ss", 0);
        dfl3.put("sn", 107);
        Map<String, Integer> dfl4 = new HashMap<>(2);
        dfl4.put("ss", 0);
        dfl4.put("sn", 108);
        Map<String, Integer> dfl5 = new HashMap<>(2);
        dfl5.put("ss", 0);
        dfl5.put("sn", 110);
        final List<Map<String, Integer>> dfl = new ArrayList<>(5);
        dfl.add(dfl1);
        dfl.add(dfl2);
        dfl.add(dfl3);
        dfl.add(dfl4);
        dfl.add(dfl5);
        allGiftMessage.put("dfl", dfl);
        client.sendMessageBinary(encoder.encode(Message.create(allGiftMessage)));
    }

    /**
     * 发送心跳消息
     */
    public void sendPantMessage() {
        final Map<String, String> pantMessage = new HashMap<>(1);
        pantMessage.put("type", "mrkl");
        client.sendMessageBinary(encoder.encode(Message.create(pantMessage)));
    }
}
