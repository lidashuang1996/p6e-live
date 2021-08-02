package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 默认的 WebSocket URL 地址 */
    private static final String DEFAULT_WEB_SOCKET_URL = "wss://danmuproxy.douyu.com:8504/";

    /** URL */
    private final String url;
    /** 处理器对象 */
    private final Handler handler;

    /** WebSocketClient 对象 */
    private Channel channel;

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String rid, LiveRoomCallback.DouYu callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(rid, new Decoder(), new Encoder(), callback, true));
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     * @param isAsync 是否异步执行
     */
    public Application(String rid, LiveRoomCallback.DouYu callback, boolean isAsync) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(rid, new Decoder(), new Encoder(), callback, isAsync));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String rid, LiveRoomCallback.DouYu callback) {
        this(url, new Handler(rid, new Decoder(), new Encoder(), callback, true));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param rid 房间的 ID
     * @param callback 回调的函数
     * @param isAsync 是否异步执行
     */
    public Application(String url, String rid, LiveRoomCallback.DouYu callback, boolean isAsync) {
        this(url, new Handler(rid, new Decoder(), new Encoder(), callback, isAsync));
    }

    /**
     * 构造方法初始化
     * @param handler 处理器对象
     */
    public Application(Handler handler) {
        this(DEFAULT_WEB_SOCKET_URL, handler);
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param handler 处理器对象
     */
    public Application(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            this.shutdown();
        }
        if (CONNECTOR == null) {
            throw new RuntimeException("connect operation connector is null.");
        } else {
            this.channel = CONNECTOR.connect(new Config(url), handler, handler.isAsync());
        }
    }

    @Override
    public void shutdown() {
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
    }
}
