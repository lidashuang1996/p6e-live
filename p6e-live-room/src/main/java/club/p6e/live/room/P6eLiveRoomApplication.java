package club.p6e.live.room;

/**
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
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
//
//    public static void createEGameLiveRoom(club.p6e.live.room.platform.egame.Application application) {
//        application.connect();
//    }
//
//    public static void createLookLiveRoom(club.p6e.live.room.platform.look.Application application) {
//        application.connect();
//    }
//
//    public static void createDouYinLiveRoom(club.p6e.live.room.platform.douyin.Application application) {
//        application.connect();
//    }

}
