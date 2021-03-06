package club.p6e.live.room.platform.douyu;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼应用
 *
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    /** 默认的 WebSocket URL 地址 */
    private static final String DEFAULT_WEB_SOCKET_URL = "wss://danmuproxy.douyu.com:8504/";

    /** 编解码器 */
    private static LiveRoomCodec<Message> CODEC = new Codec(new MessageBuilder());

    /** URL */
    private final String url;
    /** 处理器对象 */
    private final Handler handler;

    /** WebSocketClient 对象 */
    private Channel channel;

    /**
     * 读取编解码器
     * @return 编解码器
     */
    public static LiveRoomCodec<Message> getCodec() {
        return CODEC;
    }

    /**
     * 写入编解码器
     */
    public static void setCodec(LiveRoomCodec<Message> codec) {
        CODEC = codec;
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String rid, LiveRoomCallback.DouYu callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(rid, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String rid, boolean isAsync, LiveRoomCallback.DouYu callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(rid, isAsync, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String rid, LiveRoomCallback.DouYu callback) {
        this(url, new Handler(rid, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param rid 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String url, String rid, boolean isAsync, LiveRoomCallback.DouYu callback) {
        this(url, new Handler(rid, isAsync, getCodec(), callback));
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

    /**
     * 获取连接的地址
     * @return 连接的地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取处理器对象
     * @return 处理器对象
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * 获取 RID
     * @return RID
     */
    public String getRid() {
        return handler.getRid();
    }

    @Override
    public synchronized void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            LOGGER.warn("[ DouYu " + this.getRid() + " ] instance has a previous channel [ " + this.channel.id() + " ]!!");
            LOGGER.warn("[ DouYu " + this.getRid() + " ] now execute to close channel [ " + this.channel.id() + " ]...");
            this.shutdown();
            LOGGER.warn("[ DouYu " + this.getRid() + " ] closing successful [ " + this.channel.id() + " ].");
        }
        if (WEBSOCKET_CONNECTOR == null) {
            LOGGER.error("[ DouYu " + this.getRid() + " ] connect operation connector is null.");
            throw new RuntimeException("[ DouYu " + this.getRid() + " ] connect operation connector is null.");
        } else {
            // 初始化 channel
            LOGGER.info("[ DouYu " + this.getRid() + " ] start connect.");
            this.channel = WEBSOCKET_CONNECTOR.connect(new Config(url), handler, handler.isAsync());
            LOGGER.info("[ DouYu " + this.getRid() + " ] end connect channel [ " + this.channel.id() + " ].");
        }
    }

    @Override
    public synchronized void shutdown() {
        if (this.channel != null) {
            final String id = this.channel.id().toString();
            LOGGER.info("[ DouYu " + this.getRid() + " ] start closing channel [ " + id + " ].");
            this.channel.close();
            this.channel = null;
            LOGGER.info("[ DouYu " + this.getRid() + " ] end closing channel [ " + id + " ].");
        }
    }
}
