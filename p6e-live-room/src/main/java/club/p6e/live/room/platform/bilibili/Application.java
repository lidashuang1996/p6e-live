package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.Utils;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * B站: https://live.bilibili.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * B站应用
 *
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 获取房间 WebSocket 连接地址的 URL */
    private static final String URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=${room}&platform=pc&player=web";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

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
     * 通过房间编号获取 url 地址
     * @param rid 房间编号
     * @return url 地址
     */
    @SuppressWarnings("all")
    private static String[] getUrlAndToken(String rid) {
        try {
            final String httpUrl = Utils.translate(URL, "room", rid);
            LOGGER.info("[ BiliBili " + rid + " ] get url and token address ==> " + httpUrl);
            final String httpResult = Utils.doGet(httpUrl);
            LOGGER.info("[ BiliBili " + rid + " ] get url and token result ==> " + httpResult);
            final String p1 = "data", p2 = "host_server_list", p3 = "host", p4 = "wss_port", p5 = "token";
            final Map<String, Object> map = Utils.fromJsonToMap(httpResult, String.class, Object.class);
            if (map != null && map.get(p1) != null) {
                final Map<String, Object> data = (Map<String, Object>) map.get(p1);
                if (data != null && data.get(p2) != null && data.get(p5) != null) {
                    final List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(p2);
                    if (list != null && list.size() > 0) {
                        final String[] result = new String[] {
                                "wss://" + list.get(0).get(p3) + ":"
                                        + Double.valueOf(String.valueOf(list.get(0).get(p4))).intValue()  + "/sub",
                                String.valueOf(data.get(p5))
                        };
                        LOGGER.info("[ BiliBili " + rid + " ] get info result ==> " + Arrays.asList(result));
                        return result;
                    }
                }
            }
            throw new RuntimeException("[ BiliBili " + rid + " ] get url and token data error.");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String rid, LiveRoomCallback.BiLiBiLi callback) {
        final int len = 2;
        final String[] data = getUrlAndToken(rid);
        if (data != null && data.length >= len) {
            this.url = data[0];
            this.handler = new Handler(rid, data[1], true, getCodec(), callback);
        } else {
            throw new RuntimeException("[ BiliBili " + rid + " ] create application error.");
        }
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     * @param isAsync 是否异步执行
     */
    public Application(String rid, LiveRoomCallback.BiLiBiLi callback, boolean isAsync) {
        final int len = 2;
        final String[] data = getUrlAndToken(rid);
        if (data != null && data.length >= len) {
            this.url = data[0];
            this.handler = new Handler(rid, data[1], isAsync, getCodec(), callback);
        } else {
            throw new RuntimeException("[ BiliBili " + rid + " ] create application error.");
        }
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
    public String getRid() {
        return handler.getRid();
    }

    @Override
    public void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            LOGGER.warn("[ BiliBili " + this.getRid() + " ] instance has a previous channel [ " + this.channel.id() + " ]!!");
            LOGGER.warn("[ BiliBili " + this.getRid() + " ] now execute to close channel [ " + this.channel.id() + " ]...");
            this.shutdown();
            LOGGER.warn("[ BiliBili " + this.getRid() + " ] closing successful [ " + this.channel.id() + " ].");
        }
        if (WEBSOCKET_CONNECTOR == null) {
            LOGGER.error("[ BiliBili " + this.getRid() + " ] connect operation connector is null.");
            throw new RuntimeException("connect operation connector is null.");
        } else {
            // 初始化 channel
            LOGGER.info("[ BiliBili " + this.getRid() + " ] start connect.");
            this.channel = WEBSOCKET_CONNECTOR.connect(new Config(url), handler, handler.isAsync());
            LOGGER.info("[ BiliBili " + this.getRid() + " ] end connect channel [ " + this.channel.id() + " ].");
        }
    }

    @Override
    public void shutdown() {
        if (this.channel != null) {
            final String id = this.channel.id().toString();
            LOGGER.info("[ BiliBili " + this.getRid() + " ] start closing channel [ " + id + " ].");
            this.channel.close();
            this.channel = null;
            LOGGER.info("[ BiliBili " + this.getRid() + " ] end closing channel [ " + id + " ].");
        }
    }
}
