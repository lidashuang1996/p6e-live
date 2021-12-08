package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Client;
import club.p6e.live.room.platform.douyu.Message;
import club.p6e.live.room.platform.douyu.MessageBuilder;

import java.util.List;

/**
 * 弹幕服务连接器的消息回调类
 * @author lidashuang
 * @version 1.0
 */
public interface LiveRoomCallback {

    /**
     * 斗鱼直播的消息回调
     */
    public interface DouYu {
        public void onOpen(Client client );
        public void onClose(Client client );
        public void onError(Client client , Throwable throwable);
        public void onMessage(Client client, List<Message> messages);
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
//
//
//    /**
//     * BliBli直播的消息回调
//     */
//    public interface EGame {
//        public void onOpen(club.p6e.live.room.platform.egame.Client client );
//        public void onClose(club.p6e.live.room.platform.egame.Client client );
//        public void onError(club.p6e.live.room.platform.egame.Client client ,Throwable throwable);
//        public void onMessage(club.p6e.live.room.platform.egame.Client client, List<club.p6e.live.room.platform.egame.Message> messages);
//    }
//
//
//    /**
//     * BliBli直播的消息回调
//     */
//    public interface Look {
//        public void onOpen(club.p6e.live.room.platform.look.Client client );
//        public void onClose(club.p6e.live.room.platform.look.Client client );
//        public void onError(club.p6e.live.room.platform.look.Client client ,Throwable throwable);
//        public void onMessage(club.p6e.live.room.platform.look.Client client, List<club.p6e.live.room.platform.look.Message> messages);
//    }
//
//
//    /**
//     * DouYin
//     */
//    public interface DouYin {
//        public void onOpen( );
//        public void onClose( );
//        public void onError(Throwable throwable);
//        public void onMessage(club.p6e.live.room.platform.douyin.Message message);
//    }
}