package club.p6e.live.room;

import club.p6e.live.room.platform.douyin.Application;
import club.p6e.live.room.platform.douyin.Message;
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
    }
}
