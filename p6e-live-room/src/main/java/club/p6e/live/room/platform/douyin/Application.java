package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    /** 默认的请求路径 */
    private static final String DEFAULT_URL = "https://live.douyin.com/webcast/im/fetch/" +
            "?aid=6383" +
            "&live_id=1" +
            "&device_platform=web" +
            "&language=zh" +
            "&room_id=${rid}" +
            "&resp_content_type=protobuf" +
            "&version_code=9999" +
            "&identity=audience" +
            "&internal_ext=${ext}" +
            "&cursor=${cursor}" +
            "&last_rtt=${rtt}" +
            "&did_rule=3";

    /** 编解码器 */
    private static LiveRoomCodec<Message> CODEC = new Codec(new MessageBuilder());

    /** 处理器对象 */
    private final Handler handler;

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
    public Application(String rid, LiveRoomCallback.DouYin callback) {
        this(DEFAULT_URL, rid, callback);
    }

    /**
     * 构造方法初始化
     * @param url URL 模版
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String rid, LiveRoomCallback.DouYin callback) {
        this.handler = new Handler(url, rid, getCodec(), callback);
    }

    /**
     * 获取连接的地址
     * @return 连接的地址
     */
    public String getUrl() {
        return handler.getUrl();
    }

    /**
     * 获取处理器对象
     * @return 处理器对象
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * 获取处理器对象
     * @return 处理器对象
     */
    public String getHandlerId() {
        return handler.getId();
    }

    /**
     * 获取 RID
     * @return RID
     */
    public String getRid() {
        return handler.getRid();
    }
    
    @Override
    public void connect() {
        LOGGER.info("[ DouYin " + this.getRid() + " ] start connect handler [ " + this.getHandlerId() + " ].");
        this.handler.connect();
        LOGGER.info("[ DouYin " + this.getRid() + " ] end connect handler [ " + this.getHandlerId() + " ].");
    }

    @Override
    public void shutdown() {
        LOGGER.info("[ DouYin " + this.getRid() + " ] start closing handler [ " + this.getHandlerId() + " ].");
        this.handler.shutdown();
        LOGGER.info("[ DouYin " + this.getRid() + " ] end closing handler [ " + this.getHandlerId() + " ].");
    }
}
