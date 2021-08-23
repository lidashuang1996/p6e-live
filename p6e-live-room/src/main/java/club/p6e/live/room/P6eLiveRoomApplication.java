package club.p6e.live.room;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLiveRoomApplication {


    public static void init() {
        LiveRoomApplication.init();
    }

    public static void createDouYuLiveRoom(club.p6e.live.room.platform.douyu.Application application) {
        application.connect();
    }

    public static void createBiLiBiLiLiveRoom(club.p6e.live.room.platform.bilibili.Application application) {
        application.connect();
    }

    public static void createHuYaLiveRoom(club.p6e.live.room.platform.huya.Application application) {
        application.connect();
    }

    public static void createEGameLiveRoom(club.p6e.live.room.platform.egame.Application application) {
        application.connect();
    }

}
