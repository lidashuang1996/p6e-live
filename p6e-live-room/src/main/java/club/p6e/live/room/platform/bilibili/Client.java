package club.p6e.live.room.platform.bilibili;

import java.util.HashMap;
import java.util.Map;

/**
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

    /** 房间心跳的消息内容 */
    private static final String PANT_MESSAGE = "[object Object]";
    /** 房间心跳的消息类型 */
    private static final int PANT_TYPE_MESSAGE = 2;
    /** 房间登录的消息类型 */
    private static final int LOGIN_TYPE_MESSAGE = 7;
    /** 房间登录成功返回的消息类型 */
    private static final int LOGIN_RESULT_TYPE_MESSAGE = 8;

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
        final Map<String, Object> data = new HashMap<>(6);
        data.put("uid", 0);
        data.put("type", 2);
        data.put("key", token);
        // protover 采用是 2
        // protover 最新的版本是 3
        data.put("protover", 2);
        data.put("platform", "web");
        data.put("roomid", Integer.valueOf(rid));
        this.client.sendMessageBinary(encoder.encode(Message.create(data, LOGIN_TYPE_MESSAGE)));
    }

    public void sendPantMessage() {
        this.client.sendMessageBinary(encoder.encode(Message.create(PANT_MESSAGE, PANT_TYPE_MESSAGE)));
    }
}
