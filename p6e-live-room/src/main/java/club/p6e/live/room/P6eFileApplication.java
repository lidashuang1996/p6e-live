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
        for (int i = 0; i < 20; i++) {
            final int t = i;
            P6eLiveRoomApplication.createDouYuLiveRoom(new Application("475252", new LiveRoomCallback.DouYu() {
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
                        LOGGER.info("[ " + t + " ] " + message.toString());
                        message.clear();
                    }
                    messages.clear();
                }
            }));
        }
    }

}
