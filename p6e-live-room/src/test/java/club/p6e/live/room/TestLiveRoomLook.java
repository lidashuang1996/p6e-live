package club.p6e.live.room;

import club.p6e.live.room.platform.look.Application;
import club.p6e.live.room.platform.look.Client;
import club.p6e.live.room.platform.look.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestLiveRoomLook {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLiveRoomLook.class);

    @Test
    public void test() throws Exception {
        // 初始化线程池
        P6eLiveRoomApplication.init();
        // B站房间消息监听
        testRoom("1346609715");
        // 休眠主线程，保证其它线程不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     *
     * @param rid
     */
    public void testRoom(String rid) {
        // 斗鱼房间消息监听
        P6eLiveRoomApplication.createLookLiveRoom(
                new Application("wss://chatweblink03.netease.im/socket.io/1/websocket/47f7d5e8-86d0-43d9-85d2-62f79f5ee5f2", new String[]{
                        "3:::{\"SID\":13,\"CID\":2,\"SER\":1,\"Q\":[{\"t\":\"byte\",\"v\":1},{\"t\":\"Property\",\"v\":{\"1\":\"3a6a3e48f6854dfa4e4464f3bdaec3b4\",\"2\":\"\\\"nimanon_3d7ac980234a4c3122cdcb932f0809ba\\\"\",\"3\":\"b93f71ebeed9c4f9f6644a374841641c\",\"5\":113973896,\"8\":0,\"20\":\"匿名用户\",\"21\":\" \",\"26\":\"e5b78d7952afd5481a8ac2c990e86d04\",\"38\":1}},{\"t\":\"Property\",\"v\":{\"4\":\"OS X 10.15.7 64-bit\",\"6\":\"47\",\"8\":1,\"9\":1,\"13\":\"b93f71ebeed9c4f9f6644a374841641c\",\"18\":\"3a6a3e48f6854dfa4e4464f3bdaec3b4\",\"19\":\"\\\"nimanon_3d7ac980234a4c3122cdcb932f0809ba\\\"\",\"24\":\"Chrome 92.0.4515.159\",\"26\":\"e5b78d7952afd5481a8ac2c990e86d04\",\"1000\":\"\"}}]}",
                        "3:::{\"key\":0,\"ser\":1,\"code\":200,\"sid\":13,\"cid\":2,\"r\":[{\"16\":\"0\",\"1\":\"113973896\",\"3\":\"online_liveChatRoom_9226275\",\"100\":\"412974343\",\"101\":\"9\",\"102\":\"0\",\"9\":\"1\",\"12\":\"\",\"14\":\"1562764793091\",\"15\":\"1599704540716\"},{\"112\":\"1629773954000\",\"2\":\"\\\"nimanon_3d7ac980234a4c3122cdcb932f0809ba\\\"\",\"3\":\"4\",\"5\":\"匿名用户\",\"6\":\" \",\"7\":\"\",\"8\":\"1\",\"9\":\"1\",\"10\":\"1629773954000\",\"107\":\"b93f71ebeed9c4f9f6644a374841641c\",\"108\":\"e5b78d7952afd5481a8ac2c990e86d04\",\"111\":\"16\"},{}]}"
                }, new LiveRoomCallback.Look() {

                    @Override
                    public void onOpen(Client client) {
                        LOGGER.info("onOpen LOOK 回调");
                    }

                    @Override
                    public void onClose(Client client) {
                        LOGGER.info("onClose LOOK 回调");
                    }

                    @Override
                    public void onError(Client client, Throwable throwable) {
                        LOGGER.info("onError LOOK 回调");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onMessage(Client client, List<Message> messages) {
                        for (Message message : messages) {
                                LOGGER.info("onMessage LOOK 回调 ===> " + message);
                        }
                    }
                }));
    }
}
