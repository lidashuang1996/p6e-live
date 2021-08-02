package club.p6e.live.room;

import club.p6e.live.room.platform.douyu.Application;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLiveRoomApplication {


    public static void init() {
        LiveRoomApplication.init();
    }

    public static void createDouYuLiveRoomApplication(Application application) {
        application.connect();
    }

    public static LiveRoomApplication createHuYaLiveRoomApplication() {
        return null;
    }

}
