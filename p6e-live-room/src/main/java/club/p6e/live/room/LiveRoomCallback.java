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
        public void onOpen(club.p6e.live.room.platform.douyu.Client client );
        public void onClose(club.p6e.live.room.platform.douyu.Client client );
        public void onError(club.p6e.live.room.platform.douyu.Client client ,Throwable throwable);
        public void onMessage(club.p6e.live.room.platform.douyu.Client client, List<club.p6e.live.room.platform.douyu.Message> messages);
    }

    /**
     * BliBli直播的消息回调
     */
    public interface BiLiBiLi {
        public void onOpen(club.p6e.live.room.platform.bilibili.Client client );
        public void onClose(club.p6e.live.room.platform.bilibili.Client client );
        public void onError(club.p6e.live.room.platform.bilibili.Client client ,Throwable throwable);
        public void onMessage(club.p6e.live.room.platform.bilibili.Client client, List<club.p6e.live.room.platform.bilibili.Message> messages);
    }

    /**
     * BliBli直播的消息回调
     */
    public interface HuYa {
        public void onOpen(club.p6e.live.room.platform.huya.Client client );
        public void onClose(club.p6e.live.room.platform.huya.Client client );
        public void onError(club.p6e.live.room.platform.huya.Client client ,Throwable throwable);
        public void onMessage(club.p6e.live.room.platform.huya.Client client, List<club.p6e.live.room.platform.huya.Message> messages);
    }


    /**
     * BliBli直播的消息回调
     */
    public interface EGame {
        public void onOpen(club.p6e.live.room.platform.egame.Client client );
        public void onClose(club.p6e.live.room.platform.egame.Client client );
        public void onError(club.p6e.live.room.platform.egame.Client client ,Throwable throwable);
        public void onMessage(club.p6e.live.room.platform.egame.Client client, List<club.p6e.live.room.platform.egame.Message> messages);
    }
}