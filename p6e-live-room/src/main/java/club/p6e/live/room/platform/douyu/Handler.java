package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.websocket.client.Callback;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements Callback {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /** 房间 ID */
    private final String rid;
    /** 解码器 */
    private final Decoder decoder;
    /** 编码器 */
    private final Encoder encoder;
    /** 是否异步 */
    private final boolean isAsync;
    /** 回调执行函数 */
    private final LiveRoomCallback.DouYu callback;

    /**
     * 斗鱼客户端
     */
    private Client client;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     */
    public Handler(String rid, LiveRoomCallback.DouYu callback) {
        this(rid, new Decoder(), new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     */
    public Handler(String rid, LiveRoomCallback.DouYu callback, boolean isAsync) {
        this(rid, new Decoder(), new Encoder(), callback, isAsync);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param decoder 解码器
     */
    public Handler(String rid, Decoder decoder, LiveRoomCallback.DouYu callback) {
        this(rid, decoder, new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param encoder 编码器
     */
    public Handler(String rid, Encoder encoder, LiveRoomCallback.DouYu callback) {
        this(rid, new Decoder(), encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param decoder 解码器
     * @param encoder 编码器
     */
    public Handler(String rid, Decoder decoder, Encoder encoder, LiveRoomCallback.DouYu callback) {
        this(rid, decoder, encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param decoder 解码器
     * @param encoder 编码器
     * @param isAsync 是否异步执行
     */
    public Handler(String rid, Decoder decoder, Encoder encoder, LiveRoomCallback.DouYu callback, boolean isAsync) {
        this.rid = rid;
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

    @Override
    public void onOpen(club.p6e.websocket.client.Client wc) {
        // 创建客户端对象
        this.client = new Client(wc, this.encoder);
        // 发送登录的信息
        this.client.sendLoginMessage(this.rid);
        // 发送加入的组的信息
        this.client.sendGroupMessage(this.rid);
        // 发送接收全部礼物的信息
        this.client.sendAllGiftMessage();

        // 心跳任务创建
        new LiveRoomApplication.Task(45, 45, true) {
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
    public void onClose(club.p6e.websocket.client.Client client) {
        this.callback.onClose(this.client);
    }

    @Override
    public void onError(club.p6e.websocket.client.Client client, Throwable throwable) {
        this.callback.onError(this.client, throwable);
    }

    @Override
    public void onMessageText(club.p6e.websocket.client.Client client, String message) {
        LOGGER.error("[ DY: " + rid + " ] onMessageText ==> " + message
                + ", message format is incorrect and will be discarded.");
    }

    @Override
    public void onMessageBinary(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        final List<Message> messages = new ArrayList<>();
        try {
            // 解码得到消息对象
            decoder.decode(byteBuf, messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 回调收到消息方法
        this.callback.onMessage(this.client, messages);
    }

    @Override
    public void onMessagePong(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        client.sendMessagePing();
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        LOGGER.error("[ DY: " + rid + " ] onMessagePong ==> " + Arrays.toString(bytes)
                + ", message format is incorrect and will be discarded.");
    }

    @Override
    public void onMessagePing(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        client.sendMessagePong();
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        LOGGER.error("[ DY: " + rid + " ] onMessagePing ==> " + Arrays.toString(bytes)
                + ", message format is incorrect and will be discarded.");
    }

    @Override
    public void onMessageContinuation(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        LOGGER.error("[ DY: " + rid + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                + ", message format is incorrect and will be discarded.");
    }
}
