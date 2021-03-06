package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.HttpUtil;
import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /** 分割符号 */
    private static final String CHAR = "@@@";
    private static final int EXT_INDEX = 5;

    /** ID */
    private final String id;
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
        this.id = Utils.generateUuid();
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
        try {
            Signature.execute(this.id, new Signature.MessageCache(this.getTranslationUrl(),
                    content -> task(content, messages -> {
                        callback.onOpen();
                        callback.onMessage(messages);
                    })));
        } catch (IOException e) {
            e.printStackTrace();
            this.callback.onError(e);
            this.shutdown();
        }
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

    public String getStatus() {
        return status;
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

    private String msToken = "";

    /**
     * 执行任务
     * @param url 签名后的请求路径
     */
    private void task(String url, Callback callback) {
        try {
            HttpUtil.http(new HttpGet(url), httpResponse -> {
                final int statusCode = httpResponse.getStatusLine().getStatusCode();
                final InputStream inputStream = httpResponse.getEntity().getContent();

                final Header[] headers = httpResponse.getAllHeaders();
                for (Header header : headers) {
                    if ("set-cookie".equals(header.getName())) {
                        final String v = header.getValue();
                        for (String s : v.split(";")) {
                            final String[] ss = s.trim().split("=");
                            if ("msToken".equals(ss[0])) {
                               if ("".equals(msToken)) {
                                   msToken = ss[1];
                               }
                            }
                        }
                    }
                }
                System.out.println("ttt::: " + msToken);

                // 读取流释放
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

                            // 解码得到消息列表
                            final List<Message> messages = this.codec.decode(byteBuf);

                            // 下一次任务
                            nextTask(messages);

                            // 处理器处理收到的消息
                            if (callback != null) {
                                callback.execute(messages);
                            }
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

    private void nextTask(List<Message> messages) throws IOException {
        System.out.println(Utils.toJson(messages.get(0))  + "  " + Utils.toJson(messages.get(0).data()));
        final long currentDateTime = System.currentTimeMillis();
        final long rtt = currentDateTime % 1000;
        final String ext = Utils.objectToString(messages.get(0).extend().get(EXT_INDEX));
        final String[] extList = ext.split("\\|");
        final String cursor = (extList.length >= 4 && extList[3] != null && extList[3].length() > 12) ? extList[3].substring(12) : "";
        Signature.execute(this.id, new Signature.MessageCache((this.getTranslationUrl(
                ext, cursor, String.valueOf(rtt)) + ("".equals(msToken) ? "" : ("&msToken=" + msToken)) + CHAR + currentDateTime), content -> this.task(content, this.callback::onMessage)));
    }

}
