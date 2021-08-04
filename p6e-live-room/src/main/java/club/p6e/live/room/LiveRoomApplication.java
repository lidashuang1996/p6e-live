package club.p6e.live.room;

import club.p6e.websocket.client.Connector;
import club.p6e.websocket.client.P6eWebSocketClientApplication;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class LiveRoomApplication {

    private static boolean IS_INIT = false;
    /**
     * 连接器对象
     */
    protected static Connector CONNECTOR;

    /**
     * 任务队列
     */
    private static final List<Task> TASK_LIST = new CopyOnWriteArrayList<>();


    public static abstract class Task {
        /**
         * 等候时间, 单位秒
         */
        private long wait;

        /**
         * 间隔时间, 单位秒
         */
        private final long interval;

        /**
         * 是否循环执行
         */
        private final boolean loop;

        /**
         * 上次任务执行时间
         */
        private long date = 0;

        public Task(long wait, long interval, boolean loop) {
            this.wait = wait;
            this.loop = loop;
            this.interval = interval;

            // 添加任务
            TASK_LIST.add(this);
        }

        /**
         * 任务执行的内容
         */
        public abstract void execute();

        /**
         * 关闭任务
         */
        public void close() {
            TASK_LIST.remove(this);
        }
    }



    /**
     * 初始化操作
     */
    public synchronized static void init() {
        if (!IS_INIT) {
            // 初始化线程池
            P6eWebSocketClientApplication.initThreadPool();
            // 创建连接器
            CONNECTOR = P6eWebSocketClientApplication.connector();
            // 初始化任务线程
            initScheduledExecutorService();
            // 初始化完成
            IS_INIT = true;
        }
    }

    /**
     * 初始化任务线程
     */
    @SuppressWarnings("all")
    private static void initScheduledExecutorService() {
        final ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.SECONDS, new LinkedBlockingQueue(), new DefaultThreadFactory("PANT TASK"));
        threadPoolExecutor.execute(() -> {
            while (true) {
                try {
                    final long currentTime = System.currentTimeMillis() / 1000;
                    for (final Task task : TASK_LIST) {
                        try {
                            if (task.wait > 0) {
                                if (task.date == 0) {
                                    task.date = currentTime;
                                } else if (currentTime > task.date + task.wait) {
                                    task.wait = 0;
                                    task.date = currentTime;
                                    if (!task.loop) {
                                        task.close();
                                    }
                                    task.execute();
                                }
                            } else if (currentTime > task.date + task.interval) {
                                task.wait = 0;
                                task.date = currentTime;
                                if (!task.loop) {
                                    task.close();
                                }
                                task.execute();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public abstract void connect();

    public abstract void shutdown();

}
