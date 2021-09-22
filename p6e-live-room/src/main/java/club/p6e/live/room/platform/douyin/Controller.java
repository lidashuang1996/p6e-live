package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/dy")
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @RequestMapping
    public String aaa() {
        new Application("7010356842513615652", new LiveRoomCallback.DouYin() {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
            }

            @Override
            public void onClose() {
                System.out.println("onClose");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError" + throwable.getMessage());
            }

            @Override
            public void onMessage(Message message) {
                LOGGER.info(message.toString());
            }
        }).connect();
        return "success";
    }

}
