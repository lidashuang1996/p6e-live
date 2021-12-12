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

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler {

    private interface Callback {
        public void execute() throws Exception;
    }

    private final String url;
    private final String rid;
    private final LiveRoomCodec<Message> codec;
    private final LiveRoomCallback.DouYin callback;

    private volatile String status = "";

    public Handler(String url, String rid, LiveRoomCallback.DouYin callback) {
        this(url, rid, null, callback);
    }

    public Handler(String url, String rid, LiveRoomCodec<Message> codec, LiveRoomCallback.DouYin callback) {
        this.url = url;
        this.rid = rid;
        this.codec = codec;
        this.callback = callback;
    }

    void connect() {
        execute(this.getTranslationUrl(), () -> {
            this.callback.onOpen();
            this.callback.onMessage(null);
        });
    }

    void shutdown() {
        this.status = "-1";
    }

    public String getRid() {
        return rid;
    }

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
                                final String ext = Utils.objectToString(message.get(5));
                                final String[] extList = ext.split("\\|");
                                final String cursor = (extList.length >= 5
                                        && extList[4] != null && extList.length > 12) ? extList[4].substring(12) : "";
                                Thread.sleep(1000);
                                Signature.execute(id, getTranslationUrl(ext, cursor, String.valueOf(rtt)));
                            } catch (Exception e) {
                                throw new IOException(e);
                            }
                        } finally {
                            byteBuf.release();
                        }
                    }
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
