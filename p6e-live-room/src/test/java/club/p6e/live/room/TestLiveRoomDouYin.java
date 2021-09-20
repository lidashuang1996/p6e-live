package club.p6e.live.room;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomDouYin {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomDouYin.class);

    @Test
    public void test() throws Exception {
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

}
