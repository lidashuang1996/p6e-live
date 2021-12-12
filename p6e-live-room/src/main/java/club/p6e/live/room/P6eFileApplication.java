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

        // https://www.huya.com/641641
        new Application("wss://3b3fe620-ws.va.huya.com/", "1199515480194", new LiveRoomCallback.HuYa() {
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
                    LOGGER.info(message.data().toString());
                }
            }

        }).connect();
    }
}
