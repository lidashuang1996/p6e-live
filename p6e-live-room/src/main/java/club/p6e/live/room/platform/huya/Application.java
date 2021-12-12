package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.HttpUtil;
import club.p6e.live.room.utils.Utils;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 虎牙应用
 *
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    /** 默认的 WebSocket URL 地址 */
    private static final String DEFAULT_WEB_SOCKET_URL = "wss://cdnws.api.huya.com/";
    /** 默认的虎牙房间地址前缀 */
    private static final String DEFAULT_HTTP_PREFIX = "http://www.huya.com/";
    private static final String DEFAULT_HTTPS_PREFIX = "https://www.huya.com/";

    private static LiveRoomCodec<Message> CODEC = null;

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
        // 因为消息构建解构对象同时只能解构一条消息
        // 所以针对不同的应用还是每个创建一个对象使用比较好点
        if (CODEC == null) {
            return new Codec(new MessageBuilder());
        } else {
            return CODEC;
        }
    }

    /**
     * 写入编解码器
     */
    public static void setCodec(LiveRoomCodec<Message> codec) {
        CODEC = codec;
    }

    /**
     * 通过房间的 URL 获取 LiveChannelId 数据
     * @return LiveChannelId 数据
     */
    public static String getLiveChannelId(String url) {
        if (url.startsWith(DEFAULT_HTTP_PREFIX) || url.startsWith(DEFAULT_HTTPS_PREFIX)) {
            try {
                final String htmlContent = HttpUtil.doGet(url);
                System.out.println(htmlContent);
                if (!htmlContent.isEmpty()) {
                    final int minHsLength = 2;
                    final String[] hs = htmlContent.split(",\"liveChannel\":");
                    if (hs.length >= minHsLength) {
                        final String data = hs[1];
                        for (int i = 0; i < data.length(); i++) {
                            if (data.charAt(i) < '0' || data.charAt(i) > '9') {
                                return data.substring(0, i);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            throw new RuntimeException("application get live channel id, room url not is [ "
            + DEFAULT_HTTP_PREFIX + "," + DEFAULT_HTTPS_PREFIX + " ] prefix.");
        }
    }

    /**
     * 构造方法初始化
     * @param liveChannelId 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String liveChannelId, LiveRoomCallback.HuYa callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(liveChannelId, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param liveChannelId 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String liveChannelId, boolean isAsync, LiveRoomCallback.HuYa callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(liveChannelId, isAsync, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param liveChannelId 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String liveChannelId, LiveRoomCallback.HuYa callback) {
        this(url, new Handler(liveChannelId, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param liveChannelId 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String url, String liveChannelId, boolean isAsync, LiveRoomCallback.HuYa callback) {
        this(url, new Handler(liveChannelId, isAsync, getCodec(), callback));
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
     * 获取 liveChannelId
     * @return liveChannelId
     */
    public String getLiveChannelId() {
        return handler.getLiveChannelId();
    }

    @Override
    public void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            LOGGER.warn("[ HuYa " + this.getLiveChannelId() + " ] instance has a previous channel [ " + this.channel.id() + " ]!!");
            LOGGER.warn("[ HuYa " + this.getLiveChannelId() + " ] now execute to close channel [ " + this.channel.id() + " ]...");
            this.shutdown();
            LOGGER.warn("[ HuYa " + this.getLiveChannelId() + " ] closing successful [ " + this.channel.id() + " ].");
        }
        if (WEBSOCKET_CONNECTOR == null) {
            LOGGER.error("[ HuYa " + this.getLiveChannelId() + " ] connect operation connector is null.");
            throw new RuntimeException("[ HuYa " + this.getLiveChannelId() + " ] connect operation connector is null.");
        } else {
            // 初始化 channel
            LOGGER.info("[ HuYa " + this.getLiveChannelId() + " ] start connect.");
            this.channel = WEBSOCKET_CONNECTOR.connect(new Config(url), handler, handler.isAsync());
            LOGGER.info("[ HuYa " + this.getLiveChannelId() + " ] end connect channel [ " + this.channel.id() + " ].");
        }
    }

    @Override
    public void shutdown() {
        if (this.channel != null) {
            final String id = this.channel.id().toString();
            LOGGER.info("[ HuYa " + this.getLiveChannelId() + " ] start closing channel [ " + id + " ].");
            this.channel.close();
            this.channel = null;
            LOGGER.info("[ HuYa " + this.getLiveChannelId() + " ] end closing channel [ " + id + " ].");
        }
    }
}
