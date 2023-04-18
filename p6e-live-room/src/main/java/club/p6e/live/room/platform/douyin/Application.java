package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    static final String A = "1652933383919_7099294822451715340_1_1";
    static final String B = "u4MH3CggoxCQN";
    static final String C = "7099230889120402212";
    /** 默认的 WebSocket URL 地址 */
    private static final String DEFAULT_WEB_SOCKET_URL = "wss://webcast3-ws-web-lq.douyin.com/webcast/im/push/?aid=6383&app_name=douyin_web&browser_language=zh&browser_name=Mozilla&browser_online=true&browser_platform=MacIntel&browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F101.0.4951.64%20Safari%2F537.36&compress=gzip&cookie_enabled=true&cursor=" + A + "&device_platform=web&did_rule=3&identity=audience&imprp=" + B + "&live_id=1&room_id=" + C + "&screen_height=1440&screen_width=2560&tz_name=Asia%2FShanghai&update_version_code=0.0.13&version_code=180800&webcast_sdk_version=0.0.13";

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
     * @param liveChannelId 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String liveChannelId, LiveRoomCallback.DouYin callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(liveChannelId, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param liveChannelId 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String liveChannelId, boolean isAsync, LiveRoomCallback.DouYin callback) {
        this(DEFAULT_WEB_SOCKET_URL, new Handler(liveChannelId, isAsync, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param liveChannelId 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String liveChannelId, LiveRoomCallback.DouYin callback) {
        this(url, new Handler(liveChannelId, true, getCodec(), callback));
    }

    /**
     * 构造方法初始化
     * @param url WebSocket 地址
     * @param liveChannelId 房间的 ID
     * @param isAsync 是否异步执行
     * @param callback 回调的函数
     */
    public Application(String url, String liveChannelId, boolean isAsync, LiveRoomCallback.DouYin callback) {
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
        System.out.println(url);
        this.url = "wss://webcast3-ws-web-lq.douyin.com/webcast/im/push/?aid=6383&app_name=douyin_web&browser_language=zh&browser_name=Mozilla&browser_online=true&browser_platform=MacIntel&browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F101.0.4951.64%20Safari%2F537.36&compress=gzip&cookie_enabled=true&cursor=1652941135047_7099328112743171267_1_1&device_platform=web&did_rule=3&identity=audience&imprp=u4FPtCoPbjiSm&live_id=1&room_id=7099323655208979237&screen_height=1440&screen_width=2560&tz_name=Asia%2FShanghai&update_version_code=0.0.13&version_code=180800&webcast_sdk_version=0.0.13";
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
        return "";
    }

    @Override
    public void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            LOGGER.warn("[ DouYin " + this.getLiveChannelId() + " ] instance has a previous channel [ " + this.channel.id() + " ]!!");
            LOGGER.warn("[ DouYin " + this.getLiveChannelId() + " ] now execute to close channel [ " + this.channel.id() + " ]...");
            this.shutdown();
            LOGGER.warn("[ DouYin " + this.getLiveChannelId() + " ] closing successful [ " + this.channel.id() + " ].");
        }
        if (WEBSOCKET_CONNECTOR == null) {
            LOGGER.error("[ DouYin " + this.getLiveChannelId() + " ] connect operation connector is null.");
            throw new RuntimeException("[ DouYin " + this.getLiveChannelId() + " ] connect operation connector is null.");
        } else {
            // 初始化 channel
            LOGGER.info("[ DouYin " + this.getLiveChannelId() + " ] start connect.");
            String a= "" +
                    "" +
                    "" +
                    "wss://webcast3-ws-web-hl.douyin.com/webcast/im/push/?aid=6383&app_name=douyin_web&browser_language=zh&browser_name=Mozilla&browser_online=true&browser_platform=MacIntel&browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F101.0.4951.64%20Safari%2F537.36&compress=gzip&cookie_enabled=false&cursor=1653037796725_7099743272871928754_1_1&device_platform=web&did_rule=3&identity=audience&imprp=u4rkjCpefnyQf&live_id=1&room_id=7099726211026504456&screen_height=1440&screen_width=2560&tz_name=Asia%2FShanghai&update_version_code=0.0.13&version_code=180800&webcast_sdk_version=0.0.13"
                    ;
            final Config config = new Config(a);
            config.addHttpHeaders("Accept-Encoding", "gzip, deflate, br");
            config.addHttpHeaders("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8");
            config.addHttpHeaders("Origin", "https://live.douyin.com");
            config.addHttpHeaders("Host", "webcast3-ws-web-hl.douyin.com");
            config.addHttpHeaders("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36");

            config.addCookies(new Config.Cookie("THEME_STAY_TIME", "299507"));
            config.addCookies(new Config.Cookie("ttwid", "1|E7LjE0kFb8Y_VdUAFvbgKR_dFgVAjx7MOk6qId3onCA|1653007299|061ff74b758e989456a4896b2347317824a343cafa603684a09a1bf15d27b63a"));
            config.addCookies(new Config.Cookie("msToken", "yC_mg9WfL3VJSX2jRaw5MRUo7opYTdiBqRLZPdky1Az3Wbpuc2ijZ-10hLTaOM9Wf9Q-g2qkA93QZhgFJcaPXf7E3zocB1XfC8BtXZU3k7_YLErvxyRTfUkiVKFbMl39kg=="));

            this.channel = WEBSOCKET_CONNECTOR.connect(config, handler, handler.isAsync());
            LOGGER.info("[ DouYin " + this.getLiveChannelId() + " ] end connect channel [ " + this.channel.id() + " ].");
        }
    }

    @Override
    public void shutdown() {
        if (this.channel != null) {
            final String id = this.channel.id().toString();
            LOGGER.info("[ DouYin " + this.getLiveChannelId() + " ] start closing channel [ " + id + " ].");
            this.channel.close();
            this.channel = null;
            LOGGER.info("[ DouYin " + this.getLiveChannelId() + " ] end closing channel [ " + id + " ].");
        }
    }
}

