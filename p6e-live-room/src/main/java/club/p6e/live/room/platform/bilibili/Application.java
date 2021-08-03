package club.p6e.live.room.platform.bilibili;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.utils.Utils;
import club.p6e.websocket.client.Config;
import io.netty.channel.Channel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Application extends LiveRoomApplication {

    /** 官网的 URL 地址（HTTP） */
    private static final String HTTP_URL = "http://live.bilibili.com/";
    /** 官网的 URL 地址（HTTPS） */
    private static final String HTTPS_URL = "https://live.bilibili.com/";
    /** 获取房间 WebSocket 连接地址的 URL */
    private static final String WEB_SOCKET_URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=${room}&platform=pc&player=web";

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
            final String httpResult = Utils.doGet(httpUrl);
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
     * 通过直播间的网站地址获取房间编号
     * @return rid 房间编号
     */
    @SuppressWarnings("all")
    public static String getRid(String url) {
        if (url.startsWith(HTTP_URL) || url.startsWith(HTTPS_URL)) {
            try {
                final String script = "script";
                final String mark = "window.__NEPTUNE_IS_MY_WAIFU__=";
                final Document document = Jsoup.connect(url).get();
                for (final Element element : document.body().getElementsByTag(script)) {
                    String content = element.data();
                    if (content.startsWith(mark)) {
                        content = content.substring(mark.length());
                        final String p1 = "roomInfoRes", p2 = "data", p3 = "room_info", p4 = "room_id";
                        final Map<String, Object> map = Utils.fromJsonToMap(content, String.class, Object.class);
                        if (map != null && map.get(p1) != null) {
                            final Map<String, Object> map1 = (Map<String, Object>) map.get(p1);
                            if (map1.get(p2) != null) {
                                final Map<String, Object> map2 = (Map<String, Object>) map1.get(p2);
                                if (map2.get(p3) != null) {
                                    final Map<String, Object> map3 = (Map<String, Object>) map2.get(p3);
                                    if (map3.get(p4) != null) {
                                        return String.valueOf(Double.valueOf(String.valueOf(map3.get(p4))).intValue());
                                    }
                                }
                            }
                        }
                    }
                }
                throw new RuntimeException("get rid data error");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("get rid data error, " + e.getMessage());
            }
        } else {
            throw new RuntimeException("URL address does not start with " + HTTP_URL + " or " + HTTPS_URL);
        }
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     */
    public Application(String rid, LiveRoomCallback.BliBli callback) {
        final int len = 2;
        final String[] data = getUrlAndToken(rid);
        if (data != null && data.length >= len) {
            this.url = data[0];
            this.handler = new Handler(rid, data[1], new Decoder(), new Encoder(), callback, true);
        }
        throw new RuntimeException("create application error.");
    }

    /**
     * 构造方法初始化
     * @param rid 房间的 ID
     * @param callback 回调的函数
     * @param isAsync 是否异步执行
     */
    public Application(String rid, LiveRoomCallback.BliBli callback, boolean isAsync) {
        final int len = 2;
        final String[] data = getUrlAndToken(rid);
        if (data != null && data.length >= len) {
            this.url = data[0];
            this.handler = new Handler(rid, data[1], new Decoder(), new Encoder(), callback, isAsync);
        }
        throw new RuntimeException("create application error.");
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
