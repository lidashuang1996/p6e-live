package club.p6e.live.room;

import club.p6e.live.room.platform.bilibili.Application;
import club.p6e.live.room.platform.bilibili.Client;
import club.p6e.live.room.platform.bilibili.Handler;
import club.p6e.live.room.platform.bilibili.Message;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Test {

//    public static void main(String[] args) {
//        P6eLiveRoomApplication.init();
//
//        new Application(Application.getLiveChannelId("https://www.huya.com/kaerlol"), new LiveRoomCallback.HuYa() {
//
//            @Override
//            public void onOpen(Client client) {
//
//            }
//
//            @Override
//            public void onClose(Client client) {
//
//            }
//
//            @Override
//            public void onError(Client client, Throwable throwable) {
//            }
//
//            @Override
//            public void onMessage(Client client, List<Message> messages) {
//                for (Message message : messages) {
//                    System.out.println(message.data());
//                }
//            }
//        }).connect();
//    }



    public static void main(String[] args) {
        P6eLiveRoomApplication.init();
        P6eLiveRoomApplication.createBiLiBiLiLiveRoom(new Application(
                "24287913",
                new LiveRoomCallback.BiLiBiLi() {
                    @Override
                    public void onOpen(Client client) {

                    }

                    @Override
                    public void onClose(Client client) {

                    }

                    @Override
                    public void onError(Client client, Throwable throwable) {

                    }

                    @Override
                    public void onMessage(Client client, List<Message> messages) {

                    }
                }
        ));
    }
}
