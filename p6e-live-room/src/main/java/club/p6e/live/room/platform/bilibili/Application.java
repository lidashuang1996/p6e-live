package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.utils.Utils;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 获取房间 WebSocket 连接地址的 URL */
    private static final String WEB_SOCKET_URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=${room}&platform=pc&player=web";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /** URL */
    private final String url;
    /** 处理器对象 */
    private final Handler handler;

    /** WebSocketClient 对象 */
    private Channel channel;

    /**
     * 通过房间编号获取 url 地址
     * @param rid 房间编号
     * @return url 地址
     */
    @SuppressWarnings("all")
    private static String[] getUrlAndToken(String rid) {
        try {
            final String httpUrl = Utils.translate(WEB_SOCKET_URL, "room", rid);
            LOGGER.debug("get url and token address ==> " + httpUrl);
            final String httpResult = Utils.doGet(httpUrl);
            LOGGER.debug("get url and token result ==> " + httpResult);
            final String p1 = "data", p2 = "host_server_list", p3 = "host", p4 = "wss_port", p5 = "token";
            final Map<String, Object> map = Utils.fromJsonToMap(httpResult, String.class, Object.class);
            if (map != null && map.get(p1) != null) {
                final Map<String, Object> data = (Map<String, Object>) map.get(p1);
                if (data != null && data.get(p2) != null && data.get(p5) != null) {
                    final List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(p2);
                    if (list != null && list.size() > 0) {
                        return new String[] {
                                "wss://" + list.get(0).get(p3) + ":"
                                        + Double.valueOf(String.valueOf(list.get(0).get(p4))).intValue()  + "/sub",
                                String.valueOf(data.get(p5))
                        };
                    }
                }
            }
            throw new RuntimeException("get url and token data error.");
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
            this.handler = new Handler(rid, data[1], new Decoder(), new Encoder(), callback, true);
        } else {
            throw new RuntimeException("create application error.");
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
            this.handler = new Handler(rid, data[1], new Decoder(), new Encoder(), callback, isAsync);
        } else {
            throw new RuntimeException("create application error.");
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

    @Override
    public void connect() {
        // 如果之前连接上了，就关闭之前的连接，然后重新连接
        if (this.channel != null) {
            this.shutdown();
        }
        if (CONNECTOR == null) {
            throw new RuntimeException("connect operation connector is null.");
        } else {
            this.channel = CONNECTOR.connect(new Config(url), handler, handler.isAsync());
        }
    }

    @Override
    public void shutdown() {
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
    }
}
