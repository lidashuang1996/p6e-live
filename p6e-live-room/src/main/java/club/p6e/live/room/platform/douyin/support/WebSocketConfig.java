package club.p6e.live.room.platform.douyin.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 注入 web socket 执行器
     */
    @Resource
    private WebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 支持websocket 的访问链接
        registry.addHandler(handler, "/ws")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                                   ServerHttpResponse serverHttpResponse,
                                                   org.springframework.web.socket.WebSocketHandler webSocketHandler,
                                                   Map<String, Object> map) {
                        // 将请求的参数添加到 map 的对象中，方便下面的处理器使用
                        final ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
                        final HttpServletRequest request = servletRequest.getServletRequest();
                        final Enumeration<String> enumeration = request.getParameterNames();
                        while (enumeration.hasMoreElements()) {
                            String name = enumeration.nextElement();
                            map.put(name, request.getParameter(name));
                        }
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                                               ServerHttpResponse serverHttpResponse,
                                               WebSocketHandler webSocketHandler, Exception e) {
                    }
                })
                .setAllowedOrigins("*");
    }

}
