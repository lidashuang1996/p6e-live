//package club.p6e.live.room.platform.look;
//
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 斗鱼: https://www.douyu.com/
// * 开源项目地址: http://live.p6e.club/
// * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
// *
// * 斗鱼消息解码器
// * 将管道里面字节码解码为斗鱼消息对象 (club.p6e.live.room.platform.douyu.Message)
// *
// * @author lidashuang
// * @version 1.0
// */
//public class Decoder {
//
//    /** 平台名称 */
//    private static final String PLATFORM = "LOOK";
//    /** 注入日志对象 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);
//
//    public List<Message> decode(String content) {
//        // 解码的内容长度
//        LOGGER.debug("[ " + PLATFORM + " ] decode content length ==> " + content.length());
//        // 解码且将返回的对象添加到管道中
//        final List<Message> messages = new ArrayList<>();
//        boolean bool = false;
//        StringBuilder type = null;
//        for (int i = 0; i < content.length(); i++) {
//            final char ch = content.charAt(i);
//            if (ch == ':') {
//                bool = true;
//                if (i + 1 >= content.length()) {
//                    content = content.substring(i + 1);
//                    break;
//                }
//                if (content.charAt(i + 1) != ':') {
//                    content = content.substring(i + 1);
//                    break;
//                }
//            } else {
//                if (bool) {
//                  break;
//                } else if (type == null) {
//                    type = new StringBuilder(String.valueOf(ch));
//                } else {
//                    type.append(ch);
//                }
//            }
//        }
//        messages.add(Message.deserializationStringToMessage(type == null ? null : type.toString(), content));
//        return messages;
//    }
//}
