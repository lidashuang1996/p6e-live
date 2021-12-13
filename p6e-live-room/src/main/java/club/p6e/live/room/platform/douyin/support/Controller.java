package club.p6e.live.room.platform.douyin.support;

import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.platform.douyin.Application;
import club.p6e.live.room.platform.douyin.Message;
import club.p6e.live.room.utils.Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping
public class Controller {

    @RequestMapping("/")
    public Object def() {
        return "";
    }

    @RequestMapping("/douyin")
    public Object douyin() {
        new Application("7040828559455554317", new LiveRoomCallback.DouYin() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onMessage(List<Message> messages) {
                for (Message message : messages) {
                    System.out.println("DY -> " + Utils.toJson(message.data()));
                }
            }
        }).connect();
        return "SUCCESS";
    }
}
