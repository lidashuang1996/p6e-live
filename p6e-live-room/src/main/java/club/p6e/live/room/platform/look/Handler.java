package club.p6e.live.room.platform.look;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.websocket.client.Callback;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements Callback {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    private static final String PANT_TYPE = "2";

    /** 房间 ID */
    private final String[] init;
    /** 解码器 */
    private final Decoder decoder;
    /** 编码器 */
    private final Encoder encoder;
    /** 是否异步 */
    private final boolean isAsync;
    /** 回调执行函数 */
    private final LiveRoomCallback.Look callback;

    /**
     * 斗鱼客户端
     */
    private Client client;

    /**
     * 构造方法初始化
     */
    public Handler(String[] init, LiveRoomCallback.Look callback) {
        this(init, new Decoder(), new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param isAsync 是否异步执行
     */
    public Handler(String[] init, LiveRoomCallback.Look callback, boolean isAsync) {
        this(init, new Decoder(), new Encoder(), callback, isAsync);
    }

    /**
     * 构造方法初始化
     * @param decoder 解码器
     */
    public Handler(String[] init, Decoder decoder, LiveRoomCallback.Look callback) {
        this(init, decoder, new Encoder(), callback, true);
    }

    /**
     * 构造方法初始化
     * @param encoder 编码器
     */
    public Handler(String[] init, Encoder encoder, LiveRoomCallback.Look callback) {
        this(init, new Decoder(), encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param decoder 解码器
     * @param encoder 编码器
     */
    public Handler(String[] init, Decoder decoder, Encoder encoder, LiveRoomCallback.Look callback) {
        this(init, decoder, encoder, callback, true);
    }

    /**
     * 构造方法初始化
     * @param decoder 解码器
     * @param encoder 编码器
     * @param isAsync 是否异步执行
     */
    public Handler(String[] init, Decoder decoder, Encoder encoder, LiveRoomCallback.Look callback, boolean isAsync) {
        this.init = init;
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
        this.client.sendInitMessage(init);
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
    public void onMessageText(club.p6e.websocket.client.Client client, String content) {
        try {
            final List<Message> messages = decoder.decode(content);
            for (final Message message : messages) {
                if (PANT_TYPE.equals(message.type())) {
                    this.client.sendPantMessage();
                }
            }
            this.callback.onMessage(this.client, messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageBinary(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
//        LOGGER.error("[ Look:  ] onMessageText ==> " + message
//                + ", message format is incorrect and will be discarded.");
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            // this.callback.onMessage(this.client, decoder.decode(byteBuf));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePong(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        try {
            client.sendMessagePing();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ Look:  ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePing(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        try {
            client.sendMessagePong();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ Look:  ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(club.p6e.websocket.client.Client client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ Look:  ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } finally {
            byteBuf.release();
        }
    }
}
