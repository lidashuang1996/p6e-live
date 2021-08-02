package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Application;
import club.p6e.live.room.platform.douyu.Client;
import club.p6e.live.room.platform.douyu.Message;
import org.junit.Test;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomDouYu {

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createDouYuLiveRoomApplication(
                new Application("182102", new LiveRoomCallback.DouYu() {

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

            }

        }));
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

}
