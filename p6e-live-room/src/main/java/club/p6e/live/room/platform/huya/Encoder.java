package club.p6e.live.room.platform.huya;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    private static final String PLATFORM = "HuYa";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);

    private static final int WRITE_LENGTH_TYPE = 3;

    private final Taf.Encode tafEncode = new Taf.Encode();

    /**
     * 编码操作
     * 编码操作将消息对象转换为 ByteBuf 对象
     *
     * @param message 消息对象
     * @return ByteBuf 对象
     */
    public ByteBuf encode(Message message) {
        LOGGER.debug("[ " + PLATFORM + " ] encode message ==> " + message);
        try {
            final List<Object> source = message.source();
            if (source == null || source.size() <= 0) {
                throw new Exception("message content is null or size 0.");
            } else {
                final int type = (int) source.get(0);
                return tafEncode.execute(source, type == WRITE_LENGTH_TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ " + PLATFORM + " ] encode error ==> " + e.getMessage());
            return null;
        }
    }
}
