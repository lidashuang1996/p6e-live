package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.P6eWebSocketClient;
import io.netty.buffer.ByteBuf;

/**
 * B站: https://live.bilibili.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * B站客户端
 *
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    /**
     * B站: https://live.bilibili.com/
     * 开源项目地址: http://live.p6e.club/
     * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
     *
     * B站客户端增强器
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
    private static final int PANT_MESSAGE_TYPE = 2;
    /** 房间心跳的协议类型 */
    private static final int PANT_AGREEMENT_TYPE = 1;
    /** 房间登录的消息类型 */
    private static final int LOGIN_MESSAGE_TYPE = 7;
    private static final int LOGIN_AGREEMENT_TYPE = 0;
    /** 房间登录成功返回的消息类型 */
    private static final int LOGIN_RESULT_TYPE_MESSAGE = 8;

    /** RID */
    private final String rid;
    /** TOKEN */
    private final String token;
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 客户端 */
    private final P6eWebSocketClient p6eWebSocketClient;

    /**
     * 构造器初始化
     * @param rid 房间编号
     * @param token 令牌
     * @param codec 编码器
     * @param p6eWebSocketClient 客户端对象
     */
    public Client(String rid, String token, LiveRoomCodec<Message> codec, P6eWebSocketClient p6eWebSocketClient) {
        this.rid = rid;
        this.token = token;
        this.codec = codec;
        this.p6eWebSocketClient = p6eWebSocketClient;
    }

    /**
     * 发送登录消息
     */
    public void sendLoginMessage() {
        final Message message = new Message();
        message.put("uid", 0);
        message.put("type", 2);
        message.put("key", token);
        // protover 采用是 2
        // protover 最新的版本是 3
        message.put("protover", 2);
        message.put("platform", "web");
        message.put("roomid", Integer.valueOf(rid));
        message.setSpare(1);
        message.setType(LOGIN_MESSAGE_TYPE);
        message.setAgreement(LOGIN_AGREEMENT_TYPE);
        this.sendMessage(message);
    }

    /**
     * 发送心跳消息
     */
    public void sendPantMessage() {
        final Message message = new Message();
        message.setSpare(1);
        message.setData(PANT_MESSAGE);
        message.setType(PANT_MESSAGE_TYPE);
        message.setLength(PANT_MESSAGE.length());
        message.setAgreement(PANT_AGREEMENT_TYPE);
        this.sendMessage(message);
    }

    /**
     * 发送消息
     * @param message 消息对象
     */
    public void sendMessage(Message message) {
        this.p6eWebSocketClient.sendMessageBinary(this.codec.encode(message));
    }

    /**
     * 获取源的客户端对象
     * @return 源的客户端对象
     */
    public P6eWebSocketClient getP6eWebSocketClient() {
        return p6eWebSocketClient;
    }
}
