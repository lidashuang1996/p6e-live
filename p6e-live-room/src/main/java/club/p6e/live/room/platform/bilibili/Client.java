package club.p6e.live.room.platform.bilibili;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    /** 编码器 */
    private final Encoder encoder;
    /** 客户端 */
    private final club.p6e.websocket.client.Client client;


    /**
     * 构造器初始化
     * @param client 客户端
     * @param encoder 编码器
     */
    public Client(club.p6e.websocket.client.Client client, Encoder encoder) {
        this.client = client;
        this.encoder = encoder;
    }

    /**
     * 发送加入房间的信息
     * @param rid 房间的编号
     * @param token 房间的令牌
     */
    public void sendLoginMessage(String rid, String token) {
        final Map<String, Object> data = new HashMap<>();
        data.put("uid", 0);
        data.put("type", 2);
        data.put("key", token);
        data.put("protover", 3);
        data.put("platform", "web");
        data.put("roomid", Integer.valueOf(rid));
        this.client.sendMessageBinary(encoder.encode(Message.create(data)));
    }

}
