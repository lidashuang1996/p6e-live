package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.utils.HttpUtil;
import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    /** 是否开始 */
    private volatile boolean isStart = true;
    /** 是否关闭 */
    private volatile boolean isClose = false;

    /** 应用程序的 ID */
    private final String id;
    /** 房间 ID */
    private final String rid;
    /** 网络请求路径 */
    private final String url;
    /** 回调函数 */
    private final LiveRoomCallback.DouYin callback;
    private final Decoder decoder = new Decoder();

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
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.url = url;
        this.rid = rid;
        this.callback = callback;
    }

    @Override
    public void connect() {
        // 发送连接消息
        final String r = getTranslationUrl();
        Signature.set(this);
        Signature.execute(id, r);
        LOGGER.info("(DOU_YIN) [ " + rid + " ] connect ==> " + r);
    }

    @Override
    public void shutdown() {
        this.isClose = true;
        this.callback.onClose();
        Signature.delete(this);
    }

    /**
     * 获取 ID
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * 获取开始状态
     * @return 开始状态
     */
    private boolean getStartStatus() {
        try {
            return isStart;
        } finally {
            if (isStart) {
                isStart = false;
            }
        }
    }

    /**
     * 获取译文请求地址
     * @return 请求地址
     */
    private String getTranslationUrl() {
        return getTranslationUrl("", "0", "-1");
    }

    /**
     * 获取译文请求地址
     * @return 请求地址
     */
    private String getTranslationUrl(String ext, String cursor, String rtt) {
        try {
            return Utils.translate(url,
                    "rid", rid,
                    "ext", URLEncoder.encode(ext, "UTF-8"),
                    "cursor", URLEncoder.encode(cursor, "UTF-8"),
                    "rtt", URLEncoder.encode(rtt, "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行任务
     * @param url 签名后的请求路径
     */
    void execute(String url) {
        // 如果关闭就不执行
        if (this.isClose) {
            return;
        }
        try {
            HttpUtil.http(new HttpGet(url), httpResponse -> {
                final int statusCode = httpResponse.getStatusLine().getStatusCode();
                // 读取流释放
                try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                    // 如果成功就继续处理
                    if (statusCode == HttpStatus.SC_OK) {
                        final ByteBuf byteBuf = Unpooled.buffer();
                        try {
                            int i;
                            while ((i = inputStream.read()) != -1) {
                                byteBuf.writeByte(i);
                            }
                            // 处理器处理收到的消息
                            final boolean bool = getStartStatus();
                            if (bool) {
                                this.callback.onOpen();
                            }
                            final Message message = decoder.decode(byteBuf);
                            this.callback.onMessage(message);
                            try {
                                final long rtt = System.currentTimeMillis() % 1000;
                                final long sleep = Utils.objectToLong(message.get(3));
                                final String ext = Utils.objectToString(message.get(5));
                                final String[] extList = ext.split("\\|");
                                final String cursor = (extList.length >= 5
                                        && extList[4] != null && extList.length > 12) ? extList[4].substring(12) : "";
                                Thread.sleep(sleep);
                                Signature.execute(id, getTranslationUrl(ext, cursor, String.valueOf(rtt)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } finally {
                            byteBuf.release();
                        }
                    }
                }
                return null;
            }, null, null, null);
        } catch (Exception e) {
            this.callback.onError(e);
            this.shutdown();
        }
    }
}
