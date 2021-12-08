package club.p6e.live.room;

import club.p6e.live.room.platform.huya.Application;
import club.p6e.live.room.platform.huya.Client;
import club.p6e.live.room.platform.huya.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class P6eFileApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eFileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(P6eFileApplication.class, args);
        P6eLiveRoomApplication.init();
//        new Application("312212", new LiveRoomCallback.DouYu() {
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
//
//            }
//
//            @Override
//            public void onMessage(Client client, List<Message> messages) {
//
//                for (Message message : messages) {
//                    System.out.println(message.data());
//                }
//
//            }
//        }).connect();
//        new club.p6e.live.room.platform.bilibili.Application("8345602", new LiveRoomCallback.BiLiBiLi() {
//            @Override
//            public void onOpen(club.p6e.live.room.platform.bilibili.Client client) {
//
//            }
//
//            @Override
//            public void onClose(club.p6e.live.room.platform.bilibili.Client client) {
//
//            }
//
//            @Override
//            public void onError(club.p6e.live.room.platform.bilibili.Client client, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onMessage(club.p6e.live.room.platform.bilibili.Client client, List<club.p6e.live.room.platform.bilibili.Message> messages) {
//                for (club.p6e.live.room.platform.bilibili.Message message : messages) {
//                    System.out.println(message + "  " + message.data());
//                }
//            }
//        }).connect();

        new Application("294636272", new LiveRoomCallback.HuYa() {
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
                for (Message message : messages) {
                    System.out.println(message);
                }
            }
        }).connect();

        // 0010 1d00 0025 0900 0206 0e6c 6976 653a3239 3436 3336 3237 3206 0e63 6861 743a3239 3436 3336 3237 3216 002c 3600 4c5c 6600                                     f.
        // 0010 1d00 0025 0900 0206 0e6c 6976 653a3239 3436 3336 3237 3206 0e63 6861 743a3239 3436 3336 3237 3216 002c 3600 4c5c 6600
    }

}
