package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class Signature {
    /** 心跳超时时间 */
    private static final long HEARTBEAT_DATE_TIME = 100000;
    /** 心跳消息的内容 */
    private static final String HEARTBEAT_CONTENT = "{\"type\":\"heartbeat\"}";

    private static final Map<String, MessageCache> MESSAGE_CACHE = new HashMap<>();
    private static final long MESSAGE_DATE_TIME = 60000;

    public static void putMessageCache(String key, MessageCache value) {
        synchronized (MESSAGE_CACHE) {
            MESSAGE_CACHE.put(key, value);
        }

    }

    public static void removeMessageCache(String key) {
        synchronized (MESSAGE_CACHE) {
            MESSAGE_CACHE.remove(key);
        }
    }

    /**
     * 执行
     * @param id 程序 ID
     * @param message 参数
     */
    public static void execute(String id, MessageCache message) throws IOException {
        putMessageCache(id, message);
        fetchWebSocketCache().sendMessage(new TextMessage(id + "@@@" + message.getContent()));
    }

    /**
     * 执行后的回调
     * @param id 程序 ID
     * @param content 回调内容
     */
    public static void callback(String id, String content) {
        try {
            final MessageCache messageCache = MESSAGE_CACHE.get(id);
            if (messageCache != null && messageCache.getCallback() != null) {
                messageCache.getCallback().execute(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            removeMessageCache(id);
        }
    }

    /** 签名程序封装对象 */
    private static int WEBSOCKET_CACHE_INDEX = -1;
    private static final String WEBSOCKET_LOCK = "lock";
    private static final List<String> WEBSOCKET_LIST_CACHE = new ArrayList<>();
    private static final Map<String, WebSocketCache> WEBSOCKET_MAP_CACHE = new HashMap<>();

    static {
        // 心跳任务创建
        new LiveRoomApplication.Task(60, 60, true) {
            @Override
            public void execute() {
                final long currentDateTime = System.currentTimeMillis();
                try {
                    for (final String key : WEBSOCKET_LIST_CACHE) {
                        try {
                            final WebSocketCache data = WEBSOCKET_MAP_CACHE.get(key);
                            if (data != null) {
                                if (data.getWebSocketSession() != null
                                        && data.getWebSocketSession().isOpen()) {
                                    if (data.getWebSocketDateTime() + HEARTBEAT_DATE_TIME >= currentDateTime) {
                                        break;
                                    }
                                    data.getWebSocketSession().close();
                                }
                            }
                            removeWebSocketCache(key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    for (final String key : MESSAGE_CACHE.keySet()) {
                        try {
                            final MessageCache messageCache = MESSAGE_CACHE.get(key);
                            if (!(messageCache != null &&
                                    messageCache.getDateTime() + MESSAGE_DATE_TIME >= currentDateTime)) {
                                removeMessageCache(key);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }



    public static class MessageCache {

        public interface Callback {
            public void execute(String content) throws Exception;
        }

        private final long dateTime;
        private final String content;
        private final Callback callback;

        public MessageCache(String content, Callback callback) {
            this.content = content;
            this.dateTime = 60000;
            this.callback = callback;
        }

        public long getDateTime() {
            return dateTime;
        }

        public String getContent() {
            return content;
        }

        public Callback getCallback() {
            return callback;
        }
    }

    public static class WebSocketCache {
        private long webSocketDateTime;
        private final WebSocketSession webSocketSession;

        public WebSocketCache(WebSocketSession webSocketSession) {
            this.webSocketSession = webSocketSession;
            this.webSocketDateTime = System.currentTimeMillis();
        }

        public long getWebSocketDateTime() {
            return webSocketDateTime;
        }

        public WebSocketSession getWebSocketSession() {
            return webSocketSession;
        }
    }

    public synchronized static WebSocketSession fetchWebSocketCache() {
        final int size = WEBSOCKET_LIST_CACHE.size();
        if (size == 0) {
            throw new RuntimeException("no signer is available.");
        } else {
            WEBSOCKET_CACHE_INDEX = WEBSOCKET_CACHE_INDEX + 1;
            if (WEBSOCKET_CACHE_INDEX >= size) {
                WEBSOCKET_CACHE_INDEX = 0;
            }
            final String key = WEBSOCKET_LIST_CACHE.get(WEBSOCKET_CACHE_INDEX);
            final WebSocketCache data = WEBSOCKET_MAP_CACHE.get(key);
            if (data == null) {
                removeWebSocketCache(key);
                throw new RuntimeException("webSocketSession in data is empty.");
            } else {
                return data.getWebSocketSession();
            }
        }
    }

    public static void putWebSocketCache(String key, WebSocketCache value) {
        synchronized (WEBSOCKET_LOCK) {
            WEBSOCKET_LIST_CACHE.add(key);
            WEBSOCKET_MAP_CACHE.put(key, value);
        }
    }

    public static void removeWebSocketCache(String key) {
        synchronized (WEBSOCKET_LOCK) {
            WEBSOCKET_LIST_CACHE.remove(key);
            WEBSOCKET_MAP_CACHE.remove(key);
        }
    }

    public static void refreshWebSocketCache(String key) {
        final WebSocketCache data = WEBSOCKET_MAP_CACHE.get(key);
        if (data != null) {
            data.webSocketDateTime = System.currentTimeMillis();
        }
    }
}
