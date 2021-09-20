package club.p6e.live.room.platform.douyin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

/**
 * Web Socket Handler
 * @author lidashuang
 * @version 3.0
 */
@Component
public class DyWebSocketHandler implements WebSocketHandler {

    /** 分割符号 */
    private static final String CHAR = "@@@";
    /** WS 对象 */
    private static volatile WebSocketSession WS = null;
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DyWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        if (WS == null) {
            WS = webSocketSession;
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
        LOGGER.debug("[ handleMessage ] CLIENT ID: "
                + webSocketSession.getId() + " ==> message: " + webSocketMessage.getPayload());
        if (webSocketMessage instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) webSocketMessage;
            final String content = textMessage.getPayload();
            if (content.contains(CHAR)) {
                final String[] cs = content.split(CHAR);
                Signature.callback(cs[0], cs[1]);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.error("[ handleTransportError ] CLIENT ID: "
                + webSocketSession.getId() + " ==> throwable: " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        WS = null;
        LOGGER.info("[ afterConnectionClosed ] CLIENT ID: "
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
