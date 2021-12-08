package club.p6e.live.room.platform.douyu;

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
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼连接处理器对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements P6eWebSocketCallback {

    /** 日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /** RID */
    private final String rid;
    /** 是否异步 */
    private final boolean isAsync;
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 回调执行函数 */
    private final LiveRoomCallback.DouYu callback;

    /** 客户端 */
    private Client clientDouYu;
    /** 客户端增强器 */
    private Client.Intensifier clientDouYuIntensifier;
    /** 任务器 */
    private LiveRoomApplication.Task task;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param callback 回调函数
     */
    public Handler(String rid, LiveRoomCallback.DouYu callback) {
        this(rid, true, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     * @param callback 回调函数
     */
    public Handler(String rid, boolean isAsync, LiveRoomCallback.DouYu callback) {
        this(rid, isAsync, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, LiveRoomCodec<Message> codec, LiveRoomCallback.DouYu callback) {
        this(rid, true, codec, callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, boolean isAsync, LiveRoomCodec<Message> codec, LiveRoomCallback.DouYu callback) {
        this.rid = rid;
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
        this.clientDouYuIntensifier = intensifier;
    }

    /**
     * 连接成功的回调
     * @param client WebSocket 客户端
     */
    @Override
    public void onOpen(P6eWebSocketClient client) {
        try {
            // 创建客户端对象
            this.clientDouYu = new Client(this.rid, this.codec, client);
            // 增强客户端对象
            if (this.clientDouYuIntensifier != null) {
                this.clientDouYu = this.clientDouYuIntensifier.enhance(this.clientDouYu);
            }
            // 发送登录的信息
            this.clientDouYu.sendLoginMessage();
            // 发送加入的组的信息
            this.clientDouYu.sendGroupMessage();
            // 发送接收全部礼物的信息
            this.clientDouYu.sendAllGiftMessage();

            // 心跳任务创建
            // 心跳任务如果存在将关闭
            if (this.task != null) {
                final String tid = this.task.getId();
                LOGGER.warn("[ DouYu " + this.rid + " ] instance has a previous task [ " + tid + " ]!!");
                LOGGER.warn("[ DouYu " + this.rid + " ] now execute to close task [ " + tid + " ]...");
                LOGGER.info("[ DouYu: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ DouYu: " + this.rid + " ] end closing task [ " + tid + " ].");
                LOGGER.warn("[ DouYu " + this.rid + " ] closing successful [ " + tid + " ].");
            }
            this.task = new LiveRoomApplication.Task(0, 45, true) {
                @Override
                public void execute() {
                    // 心跳
                    clientDouYu.sendPantMessage();
                }
            };

            // 触发回调函数
            this.callback.onOpen(this.clientDouYu);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onOpen ==> " + e.getMessage());
        }
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
            this.callback.onError(this.clientDouYu, throwable);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onMessageText(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessageText ==> "
                    + new String(bytes, StandardCharsets.UTF_8)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessageText ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            this.callback.onMessage(this.clientDouYu, this.codec.decode(byteBuf));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessageBinary ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePong(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessagePong ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePing(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 回应 pong 的消息
            client.sendMessagePong();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.warn("[ DouYu: " + this.rid + " ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessagePing ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.warn("[ DouYu: " + this.rid + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYu: " + this.rid + " ] onMessageContinuation ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }
}
