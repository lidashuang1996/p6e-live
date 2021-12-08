//package club.p6e.live.room.platform.huya;
//
//import club.p6e.live.room.utils.Utils;
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 斗鱼: https://www.douyu.com/
// * 开源项目地址: http://live.p6e.club/
// * 开源项目地址 Github: https://github.com/lidashuang1996/p6e-live
// *
// * 斗鱼消息解码器
// * 将管道里面字节码解码为
// *
// * @author lidashuang
// * @version 1.0
// */
//public class Decoder {
//
//    /** 平台名称 */
//    private static final String PLATFORM = "HuYa";
//    /** 注入日志对象 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);
//
//    private final Taf.Decode tafDecode = new Taf.Decode();
//
//    /**
//     * 解码操作
//     * @param in 输入的对象
//     * @return 输出的对象
//     */
//    public List<Message> decode(ByteBuf in) {
//        try {
//            // 解码的内容长度
//            LOGGER.debug("[ " + PLATFORM + " ] decode content length ==> " + in.readableBytes());
//            // 解码且将返回的对象添加到管道中
//            final List<Message> list = new ArrayList<>();
//            byte[] bytes = new byte[in.readableBytes()];
//            in.readBytes(bytes);
//            System.out.println("HEX ==> " + Utils.bytesToHex(bytes));
//            in.resetReaderIndex();
//            list.add(Message.deserializationMapToMessage(tafDecode.execute(in)));
//            return list;
//        } finally {
//            tafDecode.cleanStack();
//        }
//    }
//}
