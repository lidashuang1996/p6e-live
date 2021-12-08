package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.P6eWebSocketClient;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼客户端对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    /**
     * 斗鱼: https://www.douyu.com/
     * 开源项目地址: http://live.p6e.club/
     * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
     *
     * 斗鱼客户端增强器
     *
     * @author lidashuang
     * @version 1.0
     */
    public interface Intensifier {
        /**
         * 增强原本的客户端对象
         * @param client 客户端对象
         * @return 客户端对象
         */
        public Client enhance(Client client);
    }

    /** RID */
    private final String rid;
    /** 编解码器 */
    private final LiveRoomCodec<Message> codec;
    /** WebSocket 客户端对象 */
    private final P6eWebSocketClient p6eWebSocketClient;

    /**
     * 构造方法初始化
     * @param rid RID
     * @param p6eWebSocketClient WebSocket 客户端对象
     */
    public Client(String rid, LiveRoomCodec<Message> codec, P6eWebSocketClient p6eWebSocketClient) {
        this.rid = rid;
        this.codec = codec;
        this.p6eWebSocketClient = p6eWebSocketClient;
    }

    /**
     * 发送登录的消息
     */
    public void sendLoginMessage() {
        final Message message = new Message();
        final Map<String, String> dfl = new HashMap<>(2);
        dfl.put("ss", "1");
        dfl.put("sn", "105");
        message.put("uid", "69328905");
        message.put("ct", "0");
        message.put("ver", "20190610");
        message.put("dfl", new Object[] { dfl });
        message.put("type", "loginreq");
        message.put("roomid", rid);
        message.put("username", "69328905");
        message.put("aver", "218101901");
        this.p6eWebSocketClient.sendMessageBinary(codec.encode(message));
    }

    /**
     * 发送加入组的消息
     */
    public void sendGroupMessage() {
        final Message message = new Message();
        message.put("rid", rid);
        message.put("gid", "1");
        message.put("type", "joingroup");
        this.p6eWebSocketClient.sendMessageBinary(codec.encode(message));
    }

    /**
     * 发送接收全部礼物消息
     */
    public void sendAllGiftMessage() {
        final Message message = new Message();
        message.put("type", "dmfbdreq");
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
        Map<String, Integer> dfl6 = new HashMap<>(2);
        dfl5.put("ss", 0);
        dfl5.put("sn", 901);
        message.put("dfl", new Object[] { dfl1, dfl2, dfl3, dfl4, dfl5, dfl6 });
        this.p6eWebSocketClient.sendMessageBinary(codec.encode(message));
    }

    /**
     * 发送心跳消息
     */
    public void sendPantMessage() {
        final Message message = new Message();
        message.put("type", "mrkl");
        this.p6eWebSocketClient.sendMessageBinary(codec.encode(message));
    }

    /**
     * 发送消息
     */
    public void sendMessage(Message message) {
        this.p6eWebSocketClient.sendMessageBinary(codec.encode(message));
    }
}
