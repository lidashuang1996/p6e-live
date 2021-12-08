package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.platform.douyu.Client;
import club.p6e.websocket.client.P6eWebSocketCallback;
import club.p6e.websocket.client.P6eWebSocketClient;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements P6eWebSocketCallback {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /** 房间 ID */
    private final String rid;
    /** 加入房间的令牌 */
    private final String token;
    /** 是否异步 */
    private final boolean isAsync;
    /** 回调执行函数 */
    private final LiveRoomCallback.BiLiBiLi callback;
    /** 客户端增强器 */
    private Client.Intensifier clientDouYuIntensifier;
    /** 任务器 */
    private LiveRoomApplication.Task task;

    /**
     * 斗鱼客户端
     */
    private Client client;
    /** 客户端增强器 */
    private Client.Intensifier clientDouYuIntensifier;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param callback 回调函数
     */
    public Handler(String rid, String token, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, new Decoder(), new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param callback 回调函数
     * @param isAsync 是否异步执行
     */
    public Handler(String rid, String token, LiveRoomCallback.BiLiBiLi callback, boolean isAsync) {
        this(rid, token, new Decoder(), new Encoder(), callback, isAsync);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param decoder 解码器
     * @param callback 回调函数
     */
    public Handler(String rid, String token, Decoder decoder, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, decoder, new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param encoder 编码器
     */
    public Handler(String rid, String token, Encoder encoder, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, new Decoder(), encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param decoder 解码器
     * @param encoder 编码器
     * @param callback 回调函数
     */
    public Handler(String rid, String token, Decoder decoder, Encoder encoder, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, decoder, encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param decoder 解码器
     * @param encoder 编码器
     * @param isAsync 是否异步执行
     */
    public Handler(String rid, String token, Decoder decoder, Encoder encoder, LiveRoomCallback.BiLiBiLi callback, boolean isAsync) {
        this.rid = rid;
        this.token = token;
        this.decoder = decoder;
        this.encoder = encoder;
        this.isAsync = isAsync;
        this.callback = callback;
    }

    /**
     * 是否异步执行
     * @return 异步执行的状态
     */
    public boolean isAsync() {
        return isAsync;
    }

    public String getRid() {
        return rid;
    }

    /**
     * 设置客户端增强器
     * @param intensifier 增强器对象
     */
    public void setClientIntensifier(Client.Intensifier intensifier) {
        this.clientDouYuIntensifier = intensifier;
    }

    @Override
    public void onOpen(P6eWebSocketClient wc) {
        // 创建客户端对象
        this.client = new Client(wc, this.encoder);
        // 发送登录消息
        this.client.sendLoginMessage(this.rid, this.token);

        // 心跳任务创建
        new LiveRoomApplication.Task(30, 30, true) {
            @Override
            public void execute() {
                // 心跳
                client.sendPantMessage();
            }
        };

        // 触发回调函数
        this.callback.onOpen(this.client);
    }

    @Override
    public void onClose(P6eWebSocketClient client) {
        try {
            this.callback.onClose(this.clientDouYu);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onClose ==> " + e.getMessage());
        } finally {
            if (this.task == null) {
                LOGGER.info("[ DouYu: " + this.rid + " ] no started task.");
            } else {
                final String tid = this.task.getId();
                LOGGER.info("[ DouYu: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ DouYu: " + this.rid + " ] end closing task [ " + tid + " ].");
            }
        }
    }

    @Override
    public void onError(P6eWebSocketClient client, Throwable throwable) {
        try {
            this.callback.onError(this.client, throwable);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onMessageText(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ BILI: " + this.rid + " ] onMessageText ==> "
                    + new String(bytes, StandardCharsets.UTF_8)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessageText ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            this.callback.onMessage(this.client, decoder.decode(byteBuf));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessagePong ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePong(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessagePong ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePing(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            client.sendMessagePong();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessagePing ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessageContinuation ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }
}
