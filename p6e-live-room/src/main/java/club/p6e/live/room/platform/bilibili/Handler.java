package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
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
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 回调执行函数 */
    private final LiveRoomCallback.BiLiBiLi callback;

    /** 客户端 */
    private Client clientBiliBili;
    /** 客户端增强器 */
    private Client.Intensifier clientBiliBiliIntensifier;
    /** 任务器 */
    private LiveRoomApplication.Task task;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param callback 回调函数
     */
    public Handler(String rid, String token, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, true, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param isAsync 是否异步执行
     * @param callback 回调函数
     */
    public Handler(String rid, String token, boolean isAsync, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, isAsync, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, String token, LiveRoomCodec<Message> codec, LiveRoomCallback.BiLiBiLi callback) {
        this(rid, token, true, codec, callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param token 认证的令牌
     * @param isAsync 是否异步执行
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, String token, boolean isAsync, LiveRoomCodec<Message> codec, LiveRoomCallback.BiLiBiLi callback) {
        this.rid = rid;
        this.token = token;
        this.isAsync = isAsync;
        this.codec = codec;
        this.callback = callback;
    }

    /**
     * 获取 RID
     * @return RID
     */
    public String getRid() {
        return rid;
    }

    /**
     * 是否异步执行
     * @return 异步执行的状态
     */
    public boolean isAsync() {
        return isAsync;
    }

    /**
     * 设置客户端增强器
     * @param intensifier 增强器对象
     */
    public void setClientIntensifier(Client.Intensifier intensifier) {
        this.clientBiliBiliIntensifier = intensifier;
    }

    @Override
    public void onOpen(P6eWebSocketClient client) {
        // 创建客户端对象
        this.clientBiliBili = new Client(this.rid, this.token, this.codec, client);
        // 增强客户端对象
        if (this.clientBiliBiliIntensifier != null) {
            this.clientBiliBili = this.clientBiliBiliIntensifier.enhance(this.clientBiliBili);
        }
        // 发送登录消息
        this.clientBiliBili.sendLoginMessage();

        // 心跳任务创建
        // 心跳任务如果存在将关闭
        if (this.task != null) {
            final String tid = this.task.getId();
            LOGGER.warn("[ BiliBili " + this.rid + " ] instance has a previous task [ " + tid + " ]!!");
            LOGGER.warn("[ BiliBili " + this.rid + " ] now execute to close task [ " + tid + " ]...");
            LOGGER.info("[ BiliBili: " + this.rid + " ] start closing task [ " + tid + " ].");
            this.task.close();
            this.task = null;
            LOGGER.info("[ BiliBili: " + this.rid + " ] end closing task [ " + tid + " ].");
            LOGGER.warn("[ BiliBili " + this.rid + " ] closing successful [ " + tid + " ].");
        }
        this.task = new LiveRoomApplication.Task(0, 30, true) {
            @Override
            public void execute() {
                // 心跳
                clientBiliBili.sendPantMessage();
            }
        };

        // 触发回调函数
        this.callback.onOpen(this.clientBiliBili);
    }

    @Override
    public void onClose(P6eWebSocketClient client) {
        try {
            this.callback.onClose(this.clientBiliBili);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ BiliBili: " + this.rid + " ] onClose ==> " + e.getMessage());
        } finally {
            if (this.task == null) {
                LOGGER.info("[ BiliBili: " + this.rid + " ] no started task.");
            } else {
                final String tid = this.task.getId();
                LOGGER.info("[ BiliBili: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ BiliBili: " + this.rid + " ] end closing task [ " + tid + " ].");
            }
        }
    }

    @Override
    public void onError(P6eWebSocketClient client, Throwable throwable) {
        try {
            this.callback.onError(this.clientBiliBili, throwable);
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
            LOGGER.error("[ BiliBili: " + this.rid + " ] onMessageText ==> "
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
            this.callback.onMessage(this.clientBiliBili, this.codec.decode(byteBuf));
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
