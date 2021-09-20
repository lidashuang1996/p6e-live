package club.p6e.live.room.platform.douyin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class Signature {

    /** 参数地图 */
    private static final Map<String, Application> APPLICATION_MAP = new HashMap<>();

    /**
     * 写入数据
     * @param application 应用程序
     */
    public synchronized static void set(Application application) {
        APPLICATION_MAP.put(application.getId(), application);
    }

    /**
     * 删除数据
     * @param application 应用程序
     */
    public synchronized static void delete(Application application) {
        APPLICATION_MAP.remove(application.getId());
    }

    /**
     * 执行
     * @param id 程序 ID
     * @param content 参数
     */
    public static void execute(String id, String content) {
        DyWebSocketHandler.sendMessage(id + "@@@" + content);
    }

    /**
     * 执行后的回调
     * @param id 程序 ID
     * @param content 回调内容
     */
    public static void callback(String id, String content) {
        final Application application = APPLICATION_MAP.get(id);
        if (application != null) {
            application.execute(content);
        }
    }

}
