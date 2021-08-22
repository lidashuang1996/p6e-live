package club.p6e.live.room;

import club.p6e.live.room.platform.huya.Application;
import club.p6e.live.room.platform.huya.Client;
import club.p6e.live.room.platform.huya.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomHuYa {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomHuYa.class);

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // B站房间消息监听
        testRoom("1346609715");
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void testRoom(String rid) {
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createHuYaLiveRoomApplication(
                new Application(rid, new LiveRoomCallback.HuYa() {
                    @Override
                    public void onOpen(Client client) {
                        LOGGER.info("onOpen 虎牙回调");
                    }

                    @Override
                    public void onClose(Client client) {
                        LOGGER.info("onClose 虎牙回调");
                    }

                    @Override
                    public void onError(Client client, Throwable throwable) {
                        LOGGER.info("onError 虎牙回调");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onMessage(Client client, List<Message> messages) {
                        for (Message message : messages) {
                                LOGGER.info("onMessage 虎牙回调 ===> " + message);
                        }
                    }
                }));
    }
}
