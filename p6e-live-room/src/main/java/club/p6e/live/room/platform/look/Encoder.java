package club.p6e.live.room.platform.look;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 斗鱼: https://www.douyu.com/
 * 开源项目地址: http://live.p6e.club/
 * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 斗鱼消息编码器
 * 将管道里面的斗鱼消息对象 (club.p6e.live.room.platform.douyu.Message) 编码输出
 *
 * @author lidashuang
 * @version 1.0
 */
public class Encoder {

    /** 平台名称 */
    private static final String PLATFORM = "LOOK";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);

    public String encode(Message message) {
        LOGGER.debug("[ " + PLATFORM + " ] encode message ==> " + message);
        try {
            final String type = message.type();
            final String source = message.source();
            System.out.println("fs ==> " + (type + "::" + (source == null || "".equals(source) ? "" : ":" + source)));
            return type + "::" + (source == null || "".equals(source) ? "" : ":" + source);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ " + PLATFORM + " ] encode error ==> " + e.getMessage());
            return null;
        }
    }
}
