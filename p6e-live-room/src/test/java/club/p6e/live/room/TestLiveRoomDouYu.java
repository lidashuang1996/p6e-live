package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Application;
import club.p6e.live.room.platform.douyu.Client;
import club.p6e.live.room.platform.douyu.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomDouYu {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomDouYu.class);

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // 斗鱼房间消息监听
        testRoom("288016");
        testRoom("606118");
        testRoom("99999");
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void testRoom(String rid) {
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createDouYuLiveRoom(
                new Application(rid, new LiveRoomCallback.DouYu() {

            @Override
            public void onOpen(Client client) {
                LOGGER.info("连接斗鱼回调");
            }

            @Override
            public void onClose(Client client) {
                LOGGER.info("关闭斗鱼回调");
            }

            @Override
            public void onError(Client client, Throwable throwable) {
                LOGGER.info("错误斗鱼回调");
            }

            @Override
            public void onMessage(Client client, List<Message> messages) {
                for (final Message message : messages) {
                    LOGGER.info("收到来自斗鱼的消息 ( " + rid + " ) --> " + message.data());
                }
            }

        }));
    }
}
