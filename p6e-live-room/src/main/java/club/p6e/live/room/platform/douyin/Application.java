package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

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

    private final Handler handler;

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
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String url, String rid, LiveRoomCallback.DouYin callback) {
        this.handler = new Handler(url, rid, callback);
    }

    @Override
    public void connect() {
    }

    @Override
    public void shutdown() {
    }
}
