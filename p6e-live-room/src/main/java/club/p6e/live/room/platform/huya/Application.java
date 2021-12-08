//package club.p6e.live.room.platform.huya;
//
//import club.p6e.live.room.LiveRoomApplication;
//import club.p6e.live.room.LiveRoomCallback;
//import club.p6e.websocket.client.Config;
//import io.netty.channel.Channel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public class Application extends LiveRoomApplication {
//
//    /** 注入日志对象 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
//
//    /** URL */
//    private String url = "wss://cdnws.api.huya.com/";
//    /** 处理器对象 */
//    private final Handler handler;
//
//    /** WebSocketClient 对象 */
//    private Channel channel;
//
//    /**
//     * 构造方法初始化
//     * @param rid 房间的 ID
//     * @param callback 回调的函数
//     */
//    public Application(String rid, LiveRoomCallback.HuYa callback) {
//        this.handler = new Handler(rid, new Decoder(), new Encoder(), callback, true);
//    }
//
//    public Application(String url, String rid, LiveRoomCallback.HuYa callback) {
//        this.url = url;
//        this.handler = new Handler(rid, new Decoder(), new Encoder(), callback, true);
//    }
//
//    /**
//     * 构造方法初始化
//     * @param rid 房间的 ID
//     * @param callback 回调的函数
//     * @param isAsync 是否异步执行
//     */
//    public Application(String rid, LiveRoomCallback.HuYa callback, boolean isAsync) {
//        this.handler = new Handler(rid, new Decoder(), new Encoder(), callback, isAsync);
//    }
//
//    public Application(String url, String rid, LiveRoomCallback.HuYa callback, boolean isAsync) {
//        this.url = url;
//        this.handler = new Handler(rid, new Decoder(), new Encoder(), callback, isAsync);
//    }
//
//    /**
//     * 构造方法初始化
//     * @param url WebSocket 地址
//     * @param handler 处理器对象
//     */
//    public Application(String url, Handler handler) {
//        this.url = url;
//        this.handler = handler;
//    }
//
//    @Override
//    public void connect() {
//        // 如果之前连接上了，就关闭之前的连接，然后重新连接
//        if (this.channel != null) {
//            this.shutdown();
//        }
//        if (CONNECTOR == null) {
//            throw new RuntimeException("connect operation connector is null.");
//        } else {
//            CONNECTOR.connect(new Config(url), handler, handler.isAsync());
//        }
//    }
//
//    @Override
//    public void shutdown() {
//        if (this.channel != null) {
//            this.channel.close();
//            this.channel = null;
//        }
//    }
//}
