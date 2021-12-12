package club.p6e.live.room.platform.douyin.support;

import club.p6e.live.room.platform.douyin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class WebSocketHandler implements org.springframework.web.socket.WebSocketHandler {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    /** 分割符号 */
    private static final String CHAR = "@@@";
    /** 心跳超时时间 */
    private static final long HEARTBEAT_DATE_TIME = 100000;
    /** 心跳消息的内容 */
    private static final String HEARTBEAT_CONTENT = "{\"type\":\"heartbeat\"}";

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        // 添加缓存
        Signature.putWebSocketCache(webSocketSession.getId(), new Signature.WebSocketCache(webSocketSession));
        webSocketSession.sendMessage(new TextMessage("[ " + webSocketSession.getId() + " ] client connect success"));
        LOGGER.info("[ DouYin afterConnectionEstablished ] client ID:  " +  webSocketSession.getId() + ", client is successfully connected.");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws IOException {
        LOGGER.debug("[ DouYin handleMessage ] client ID: "
                + webSocketSession.getId() + " ==> message: " + webSocketMessage.getPayload());
        if (webSocketMessage instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) webSocketMessage;
            final String content = textMessage.getPayload();
            if (content.contains(CHAR)) {
                final String[] cs = content.split(CHAR);
                Signature.callback(cs[0], cs[1]);
            } else if (HEARTBEAT_CONTENT.equals(content)) {
                // 刷新缓存
                Signature.refreshWebSocketCache(webSocketSession.getId());
                webSocketSession.sendMessage(new TextMessage(HEARTBEAT_CONTENT));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.error("[ DouYin handleTransportError ] client ID: "
                + webSocketSession.getId() + " ==> throwable: " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        // 删除缓存
        Signature.removeWebSocketCache(webSocketSession.getId());
        LOGGER.info("[ DouYin afterConnectionClosed ] client ID: "
                + webSocketSession.getId() + " ==> closeStatus: " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
