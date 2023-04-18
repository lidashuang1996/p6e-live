package club.p6e.live.room;

import club.p6e.live.room.platform.douyin.Application;
import club.p6e.live.room.platform.douyin.Client;
import club.p6e.live.room.platform.douyin.Message;

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
        new Application("123456", new LiveRoomCallback.DouYin() {
            @Override
            public void onOpen(Client client) {
                System.out.println("::::::::onOpen");
            }

            @Override
            public void onClose(Client client) {
                System.out.println("::::::::onClose");
            }

            @Override
            public void onError(Client client, Throwable throwable) {
                System.out.println("::::::::onError");
            }

            @Override
            public void onMessage(Client client, List<Message> messages) {
                System.out.println("::::::::onMessage");
            }
        })
                .connect();
    }
}
