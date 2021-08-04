package club.p6e.live.room;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLiveRoomApplication {


    public static void init() {
        LiveRoomApplication.init();
    }

    public static void createDouYuLiveRoomApplication(club.p6e.live.room.platform.douyu.Application application) {
        application.connect();
    }

    public static void createBiLiBiLiLiveRoomApplication(club.p6e.live.room.platform.bilibili.Application application) {
        application.connect();
    }

    public static LiveRoomApplication createHuYaLiveRoomApplication() {
        return null;
    }

}
