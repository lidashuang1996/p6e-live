package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.HttpUtil;
import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler {

    /**
     * 处理器的回调函数对象
     */
    private interface Callback {

        /**
         * 执行回调
         * @param messages 消息对象
         * @throws Exception 出现的异常
         */
        public void execute(List<Message> messages) throws Exception;
    }

    private String id;
    /** URL */
    private final String url;
    /** RID */
    private final String rid;
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 回调执行函数 */
    private final LiveRoomCallback.DouYin callback;

    /** 当前请求的状态 */
    private volatile String status = "";

    /**
     * 构造方法初始化
     * @param url 请求的 URL 模版
     * @param rid 房间 ID
     * @param callback 回调函数
     */
    public Handler(String url, String rid, LiveRoomCallback.DouYin callback) {
        this(url, rid, null, callback);
    }

    /**
     * 构造方法初始化
     * @param url 请求的 URL 模版
     * @param rid 房间 ID
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String url, String rid, LiveRoomCodec<Message> codec, LiveRoomCallback.DouYin callback) {
        this.url = url;
        this.rid = rid;
        this.codec = codec;
        this.callback = callback;
    }

    /**
     * 连接服务
     */
    void connect() {
        // 开始执行连接
        execute(this.getTranslationUrl(), (List<Message> messages) -> {
            this.callback.onOpen();
            this.callback.onMessage(messages);
        });
    }

    /**
     * 关闭服务
     */
    void shutdown() {
        // 关闭服务
        this.status = "-1";
        this.callback.onClose();
    }

    /**
     * 获取 RID
     * @return RID
     */
    public String getRid() {
        return rid;
    }

    public String getId() {
        return id;
    }

    /**
     * 获取 URL
     * @return URL
     */
    public String getUrl() {
        return url;
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
    private void execute(String url, Callback callback) {
        try {
            HttpUtil.http(new HttpGet(url), httpResponse -> {
                final int statusCode = httpResponse.getStatusLine().getStatusCode();
                final InputStream inputStream = httpResponse.getEntity().getContent();
                // 读取流释放≤
                try {
                    // 如果成功就继续处理
                    if (statusCode == HttpStatus.SC_OK) {
                        final ByteBuf byteBuf = Unpooled.buffer();
                        try {
                            // 读取数据
                            int i;
                            while ((i = inputStream.read()) != -1) {
                                byteBuf.writeByte(i);
                            }
                            // 处理器处理收到的消息
                            if (callback != null) {
                                callback.execute(this.codec.decode(byteBuf));
                            }
//                            try {
//                                final long rtt = System.currentTimeMillis() % 1000;
//                                final String ext = Utils.objectToString(message.get(5));
//                                final String[] extList = ext.split("\\|");
//                                final String cursor = (extList.length >= 5
//                                        && extList[4] != null && extList.length > 12) ? extList[4].substring(12) : "";
//                                Thread.sleep(1000);
//                                Signature.execute(id, getTranslationUrl(ext, cursor, String.valueOf(rtt)));
//                            } catch (Exception e) {
//                                throw new IOException(e);
//                            }
                        } catch (Exception e) {
                            throw new IOException(e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                } finally {
                    inputStream.close();
                }
                return null;
            }, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            this.callback.onError(e);
            this.shutdown();
        }
    }

}
