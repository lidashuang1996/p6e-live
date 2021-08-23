package club.p6e.live.room;

import club.p6e.live.room.platform.bilibili.Application;
import club.p6e.live.room.platform.bilibili.Client;
import club.p6e.live.room.platform.bilibili.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomBiLiBiLi {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomBiLiBiLi.class);

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // B站房间消息监听
        testRoom("316483");
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void testRoom(String rid) {
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createBiLiBiLiLiveRoom(
                new Application(rid, new LiveRoomCallback.BiLiBiLi() {
            @Override
            public void onOpen(Client client) {
                LOGGER.info("连接B站回调");
            }

            @Override
            public void onClose(Client client) {
                LOGGER.info("关闭B站回调");
            }

            @Override
            public void onError(Client client, Throwable throwable) {
                LOGGER.info("错误B站回调");
            }

            @Override
            public void onMessage(Client client, List<Message> messages) {
                for (final Message message : messages) {
                    LOGGER.info("收到来自B站的消息 ( " + rid + " ) --> " + message.data());
                }
            }
        }));
    }
}
