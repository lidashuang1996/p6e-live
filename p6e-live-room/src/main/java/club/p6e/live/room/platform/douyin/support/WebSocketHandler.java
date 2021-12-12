package club.p6e.live.room.platform.douyin.support;

import club.p6e.live.room.LiveRoomApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 3.0
 */
@Component
public class WebSocketHandler implements org.springframework.web.socket.WebSocketHandler {

    private static class Data {
        private long webSocketDateTime = -1L;
        private WebSocketSession webSocketSession;
    }

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    /** 分割符号 */
    private static final String CHAR = "@@@";
    /** 心跳超时时间 */
    private static final long HEARTBEAT_DATE_TIME = 100000;
    /** 心跳消息的内容 */
    private static final String HEARTBEAT_CONTENT = "{\"type\":\"heartbeat\"}";
    /** 签名程序封装对象 */
    private static final Map<String, Data> WEBSOCKET_CACHE = new HashMap<>();

    static {
        // 心跳任务创建
        new LiveRoomApplication.Task(60, 60, true) {
            @Override
            public void execute() {
                if (WS != null) {
                    if (WS.isOpen()) {
                        if (System.currentTimeMillis() + HEARTBEAT_DATE_TIME <= WS_DATE_TIME) {
                            // 发送心跳
                            sendMessage(HEARTBEAT_CHAR);
                            return;
                        }
                    }
                    try {
                        WS.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private static void putWebSocketCache(String key, Data value) {
        synchronized (WEBSOCKET_CACHE) {
            WEBSOCKET_CACHE.put(key, value);
        }
    }

    private static void removeWebSocketCache(String key) {
        synchronized (WEBSOCKET_CACHE) {
            WEBSOCKET_CACHE.get(key);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        if (WS == null) {
            WS = webSocketSession;
            WS_DATE_TIME = System.currentTimeMillis();
            sendMessage("[ " + webSocketSession.getId() + " ] client connect success");
            LOGGER.info("[ " + webSocketSession.getId() + " ] client is successfully connected.");
        } else {
            if (!WS.isOpen()) {
                WS = webSocketSession;
                LOGGER.info("[ " + webSocketSession.getId() + " ] client is successfully connected.");
            } else {
                try {
                    webSocketSession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LOGGER.error("[ " + webSocketSession.getId() + " ] client already exists, connection refused.");
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        LOGGER.debug("[ DouYin handleMessage ] CLIENT ID: "
                + webSocketSession.getId() + " ==> message: " + webSocketMessage.getPayload());
        if (webSocketMessage instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) webSocketMessage;
            final String content = textMessage.getPayload();
            if (content.contains(CHAR)) {
                final String[] cs = content.split(CHAR);
                Signature.callback(cs[0], cs[1]);
            } else if (HEARTBEAT_CHAR.equals(content)) {
                WS_DATE_TIME = System.currentTimeMillis();
                sendMessage(HEARTBEAT_CHAR);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.error("[ DouYin handleTransportError ] CLIENT ID: "
                + webSocketSession.getId() + " ==> throwable: " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        WS = null;
        WS_DATE_TIME = -1;
        LOGGER.info("[ DouYin afterConnectionClosed ] CLIENT ID: "
                + webSocketSession.getId() + " ==> closeStatus: " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息
     * @param content 消息内容
     */
    public static void sendMessage(String content) {
        try {
            if (WS != null && WS.isOpen()) {
                WS.sendMessage(new TextMessage(content));
            } else {
                throw new RuntimeException("web socket client not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
