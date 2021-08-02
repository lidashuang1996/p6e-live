package club.p6e.live.room;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/**
 * 弹幕服务连接器的消息回调类
 * @author lidashuang
 * @version 1.0
 */
public interface LiveRoomCallback {

    public void onOpen(ChannelHandlerContext context, List<club.p6e.live.room.platform.douyu.Message> messages);
    public void onClose(ChannelHandlerContext context, List<club.p6e.live.room.platform.douyu.Message> messages);
    public void onError(ChannelHandlerContext context, List<club.p6e.live.room.platform.douyu.Message> messages);

    /**
     * 斗鱼直播的消息回调
     */
    public interface DouYu {
        /**
         * 回调的执行方法
         * @param messages 消息对象
         */
        public void onOpen(club.p6e.live.room.platform.douyu.Client client );
        public void onClose(club.p6e.live.room.platform.douyu.Client client );
        public void onError(club.p6e.live.room.platform.douyu.Client client ,Throwable throwable);
        public void onMessage(club.p6e.live.room.platform.douyu.Client client, List<club.p6e.live.room.platform.douyu.Message> messages);
    }




//    /**
//     * BliBli直播的消息回调
//     */
//    public interface BliBli {
//        /**
//         * 回调的执行方法
//         * @param messages 消息对象
//         */
//        public void execute(List<P6eBliBliChannelMessage> messages);
//    }
//
//    /**
//     * 火猫直播的消息回调
//     */
//    public interface HuoMao {
//        /**
//         * 回调的执行方法
//         * @param messages 消息对象
//         */
//        public void execute(List<P6eHuoMaoChannelMessage> messages);
//    }
//
//    /**
//     * 快手直播的消息回调
//     */
//    public interface KuaiShou {
//        /**
//         * 回调的执行方法
//         * @param messages 消息对象
//         */
//        public void execute(List<P6eKuaiShouChannelMessage> messages);
//    }
}