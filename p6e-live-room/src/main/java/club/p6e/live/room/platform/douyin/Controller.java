package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/douyin")
public class Controller {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @RequestMapping("/{id}")
    public String id(@PathVariable String id) {
        new Application(id, new LiveRoomCallback.DouYin() {
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
                getData2(message.get(1));
            }
        }).connect();
        return "SUCCESS";
    }

    private static Object getData(Object aa, Object key) {
        if (aa instanceof Map) {
            Map m = (Map) aa;
            return m.get(key);
        }
        return null;
    }

    private static void getData2(Object o) {
        if (o instanceof List) {
            ((List<?>) o).forEach(item -> {
                getData2(item);
            });
        } else if (o instanceof Map) {
            fl(((Map<?, ?>) o).get(2));
        }
    }

    private static void fl (Object o) {
        final String type = String.valueOf(getData(getData(o, 1), 1));
        switch (type) {
            case "WebcastChatMessage":
                System.out.println("[弹幕]  " +  getData(getData(o, 2), 3) + "  说==>  " + getData(o, 3));
                break;
            case "WebcastMemberMessage":
                System.out.println("[房间]  " +  getData(getData(o, 2), 3) + "  进入了房间");
                break;
            case "WebcastGiftMessage":
                System.out.println("[礼物]  " +  getData(getData(o, 7), 3) + "  送出==>  (" + getData(getData(o, 15), 5) + ") " + getData(getData(o, 15), 16)
                        + "  数量为 " + getData(getData(o, 15), 7)
                        + "  数量为 " + getData(getData(o, 15), 8)
                        + "  数量为 " + getData(getData(o, 15), 10)
                        + "  数量为 " + getData(getData(o, 15), 11)
                        + "  数量为 " + getData(getData(o, 15), 12)
                        + "  数量为 " + getData(getData(o, 15), 13)
                );
                break;
        }
    }

}
