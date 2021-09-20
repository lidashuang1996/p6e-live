package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/dy")
public class Controller {

    @RequestMapping
    public String aaa() {
        new Application("7009898332214545189", new LiveRoomCallback.DouYin() {
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
            public void onMessage(List<Message> messages) {
                System.out.println("messages" + messages);
            }
        }).connect();
        return "success";
    }

}
