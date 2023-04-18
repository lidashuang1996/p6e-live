package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.LiveRoomApplication;
import club.p6e.live.room.LiveRoomCallback;
import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.P6eWebSocketCallback;
import club.p6e.websocket.client.P6eWebSocketClient;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements P6eWebSocketCallback {

    /** 日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /** RID */
    private final String rid;
    /** 是否异步 */
    private final boolean isAsync;
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 回调执行函数 */
    private final LiveRoomCallback.DouYin callback;

    /** 客户端 */
    private Client clientDouYin;
    /** 客户端增强器 */
    private Client.Intensifier clientDouYinIntensifier;
    /** 任务器 */
    private LiveRoomApplication.Task task;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, boolean isAsync, LiveRoomCodec<Message> codec, LiveRoomCallback.DouYin callback) {
        this.rid = rid;
        this.isAsync = isAsync;
        this.codec = codec;
        this.callback = callback;
    }

    /**
     * 获取 RID
     * @return RID
     */
    public String getRid() {
        return rid;
    }

    /**
     * 是否异步执行
     * @return 异步执行的状态
     */
    public boolean isAsync() {
        return isAsync;
    }

    /**
     * 设置客户端增强器
     * @param intensifier 增强器对象
     */
    public void setClientIntensifier(Client.Intensifier intensifier) {
        this.clientDouYinIntensifier = intensifier;
    }

    /**
     * 连接成功的回调
     * @param client WebSocket 客户端
     */
    @Override
    public void onOpen(P6eWebSocketClient client) {
        System.out.println("xxxxxxxdddddddddyyyyyyy " + client);
//        try {
//            // 创建客户端对象
//            this.clientDouYin = new Client(this.rid, this.codec, client);
//            // 增强客户端对象
//            if (this.clientDouYinIntensifier != null) {
//                this.clientDouYin = this.clientDouYinIntensifier.enhance(this.clientDouYin);
//            }
//            // 发送登录的信息
//            this.clientDouYin.sendLoginMessage();
//            // 发送加入的组的信息
//            this.clientDouYin.sendGroupMessage();
//            // 发送接收全部礼物的信息
//            this.clientDouYin.sendAllGiftMessage();
//
//            // 心跳任务创建
//            // 心跳任务如果存在将关闭
//            if (this.task != null) {
//                final String tid = this.task.getId();
//                LOGGER.warn("[ DouYin " + this.rid + " ] instance has a previous task [ " + tid + " ]!!");
//                LOGGER.warn("[ DouYin " + this.rid + " ] now execute to close task [ " + tid + " ]...");
//                LOGGER.info("[ DouYin: " + this.rid + " ] start closing task [ " + tid + " ].");
//                this.task.close();
//                this.task = null;
//                LOGGER.info("[ DouYin: " + this.rid + " ] end closing task [ " + tid + " ].");
//                LOGGER.warn("[ DouYin " + this.rid + " ] closing successful [ " + tid + " ].");
//            }
//            this.task = new LiveRoomApplication.Task(0, 45, true) {
//                @Override
//                public void execute() {
//                    // 心跳
//                    clientDouYin.sendPantMessage();
//                }
//            };
//
//            // 触发回调函数
//            this.callback.onOpen(this.clientDouYin);
//
//            System.out.println("XXXXXXX");
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("[ DouYin: " + this.rid + " ] onOpen ==> " + e.getMessage());
//        }
    }

    @Override
    public void onClose(P6eWebSocketClient client) {
        try {
            this.callback.onClose(this.clientDouYin);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onClose ==> " + e.getMessage());
        } finally {
            if (this.task == null) {
                LOGGER.info("[ DouYin: " + this.rid + " ] no started task.");
            } else {
                final String tid = this.task.getId();
                LOGGER.info("[ DouYin: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ DouYin: " + this.rid + " ] end closing task [ " + tid + " ].");
            }
        }
    }

    @Override
    public void onError(P6eWebSocketClient client, Throwable throwable) {
        try {
            this.callback.onError(this.clientDouYin, throwable);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onMessageText(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessageText ==> "
                    + new String(bytes, StandardCharsets.UTF_8)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessageText ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            System.out.println("--> " +   byteBuf.readableBytes());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessageBinary ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePong(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessagePong ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePing(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 回应 pong 的消息
            client.sendMessagePong();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.warn("[ DouYin: " + this.rid + " ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessagePing ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.warn("[ DouYin: " + this.rid + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ DouYin: " + this.rid + " ] onMessageContinuation ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }
}
