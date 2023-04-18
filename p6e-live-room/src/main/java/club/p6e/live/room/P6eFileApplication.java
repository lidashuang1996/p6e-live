package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Application;
import club.p6e.live.room.platform.douyu.Client;
import club.p6e.live.room.platform.douyu.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class P6eFileApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eFileApplication.class);

    /**
     * 斗鱼推送过来的消息类型
     * --- 弹幕的消息类型
     */
    public static final String BARRAGE_TYPE = "chatmsg";

    /**
     * 斗鱼推送过来的消息类型
     * --- 礼物的消息类型
     */
    public static final String GIFT_TYPE = "dgb";

    /**
     * 斗鱼推送过来的消息类型
     * --- 用户徽章升级消息类型
     */
    public static final String BADGE_UPGRADE_TYPE = "blab";

    /**
     * 斗鱼推送过来的消息类型
     * --- 禁止说话的消息类型
     */
    public static final String NO_TALKING = "newblackres";

    /**
     * 斗鱼推送过来的消息类型
     * --- 进入房间的消息类型
     */
    public static final String ENTER_ROOM_TYPE = "uenter";

    /**
     * 斗鱼推送过来的消息类型
     * --- 粉丝排行版的消息类型
     */
    public static final String FANS_RANKING_TYPE = "frank";

    /**
     * 斗鱼推送过来的消息类型
     * --- 主播离开提醒的消息类型
     */
    public static final String ANCHOR_LEAVE_TYPE = "al";

    /**
     * 斗鱼推送过来的消息类型
     * --- 主播回来提醒的消息类型
     */
    public static final String ANCHOR_BACK_TYPE = "ab";


    /**
     * 斗鱼推送过来的消息类型
     * --- 等级排行榜的消息类型
     */
    public static final String RANK_RANKING_TYPE = "ul_ranklist";

    /**
     * 斗鱼推送过来的消息类型
     * --- 贵族排行榜的消息类型
     */
    public static final String NOBLE_RANKING_TYPE = "online_noble_list";

    /**
     * 斗鱼推送过来的消息类型
     * --- 分享的消息类型
     */
    public static final String SHARE_TYPE = "srres";

    /**
     * 斗鱼推送过来的消息类型
     * --- 栏目排行榜的消息类型
     */
    public static final String COURSE_RANKING_TYPE = "rri";

    /**
     * 斗鱼推送过来的消息类型
     * --- 广播排行榜的消息类型
     */
    public static final String BROADCAST_RANKING_TYPE = "ranklist";


    /**
     * 斗鱼推送过来的消息类型
     * --- 主播等级提升的消息类型
     */
    public static final String ANCHOR_RANK_RAISE_TYPE = "upbc";

    /**
     * 斗鱼推送过来的消息类型
     * --- 房间开关播的消息类型
     */
    public static final String ANCHOR_SWITCH_TYPE = "rss";


    /**
     * 斗鱼推送过来的消息类型
     * --- TOP 10 的消息类型
     */
    public static final String TOP_10_RANKING_TYPE = "rankup";

    /**
     * 斗鱼推送过来的消息类型
     * --- 用户升级的消息类型
     */
    public static final String USER_UPGRADE_TYPE = "upgrade";

    /**
     * 斗鱼推送过来的消息类型
     * --- 登陆成功的消息类型
     */
    public static final String LOGIN_TYPE = "loginres";

    /**
     * 斗鱼推送过来的消息类型
     * --- 心跳的消息类型
     */
    public static final String PANT_TYPE = "mrkl";

    /**
     * 斗鱼推送过来的消息类型
     * --- 开启礼物全部接受的消息类型
     */
    public static final String GIFT_ALL_TYPE = "dmfbdres";

    public static void main(String[] args) {
//        SpringApplication.run(P6eFileApplication.class, args);
        P6eLiveRoomApplication.init();
        P6eLiveRoomApplication.createDouYuLiveRoom(new Application("182102", new LiveRoomCallback.DouYu() {
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
                for (final Message message : messages) {
                    final Map<String, Object> map = ((Map) message.data());
                    final String type = map.get("type") == null ? null : String.valueOf(map.get("type"));
                    if (!(BARRAGE_TYPE.equals(type)
                            || GIFT_TYPE.equals(type)
                            || BADGE_UPGRADE_TYPE.equals(type)
                            || NO_TALKING.equals(type)
                            || ENTER_ROOM_TYPE.equals(type)
                            || FANS_RANKING_TYPE.equals(type)
                            || ANCHOR_LEAVE_TYPE.equals(type)
                            || ANCHOR_BACK_TYPE.equals(type)
                            || RANK_RANKING_TYPE.equals(type)
                            || NOBLE_RANKING_TYPE.equals(type)
                            || SHARE_TYPE.equals(type)
                            || COURSE_RANKING_TYPE.equals(type)
                            || BROADCAST_RANKING_TYPE.equals(type)
                            || ANCHOR_RANK_RAISE_TYPE.equals(type)
                            || ANCHOR_SWITCH_TYPE.equals(type)
                            || TOP_10_RANKING_TYPE.equals(type)
                            || USER_UPGRADE_TYPE.equals(type)
                    )) {
                        LOGGER.info(map.toString());
                    }
                }
            }
        }));
    }
}
