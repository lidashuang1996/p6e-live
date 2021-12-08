package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Application;
import club.p6e.live.room.platform.douyu.Client;
import club.p6e.live.room.platform.douyu.Message;
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
        new club.p6e.live.room.platform.bilibili.Application("9922197", new LiveRoomCallback.BiLiBiLi() {
            @Override
            public void onOpen(club.p6e.live.room.platform.bilibili.Client client) {

            }

            @Override
            public void onClose(club.p6e.live.room.platform.bilibili.Client client) {

            }

            @Override
            public void onError(club.p6e.live.room.platform.bilibili.Client client, Throwable throwable) {

            }

            @Override
            public void onMessage(club.p6e.live.room.platform.bilibili.Client client, List<club.p6e.live.room.platform.bilibili.Message> messages) {
                for (club.p6e.live.room.platform.bilibili.Message message : messages) {
                    System.out.println(message + "  " + message.data());
                }
            }
        }).connect();
    }

}
