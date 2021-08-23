package club.p6e.live.room;

import club.p6e.live.room.platform.egame.Application;
import club.p6e.live.room.platform.egame.Client;
import club.p6e.live.room.platform.egame.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomEGame {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomEGame.class);

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // B站房间消息监听
        testRoom("");
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     *
     * @param rid
     */
    public void testRoom(String rid) {
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createEGameLiveRoom(
                new Application(rid, new LiveRoomCallback.EGame() {
                    @Override
                    public void onOpen(Client client) {
                        LOGGER.info("onOpen EGame");
                    }

                    @Override
                    public void onClose(Client client) {
                        LOGGER.info("onClose EGame");
                    }

                    @Override
                    public void onError(Client client, Throwable throwable) {
                        LOGGER.info("onError EGame");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onMessage(Client client, List<Message> messages) {
                        for (Message message : messages) {
                                LOGGER.info("onMessage EGame ===> " + message);
                        }
                    }
                }));
    }
}
