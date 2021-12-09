package club.p6e.live.room.platform.huya;

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
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 虎牙连接处理器对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements P6eWebSocketCallback {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /** 房间 ID */
    private final String rid;
    /** 是否异步 */
    private final boolean isAsync;
    /** 编码器 */
    private final LiveRoomCodec<Message> codec;
    /** 回调执行函数 */
    private final LiveRoomCallback.HuYa callback;

    /** 客户端 */
    private Client clientHuYa;
    /** 客户端增强器 */
    private Client.Intensifier clientHuYaIntensifier;
    /** 任务器 */
    private LiveRoomApplication.Task task;

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param callback 回调函数
     */
    public Handler(String rid, LiveRoomCallback.HuYa callback) {
        this(rid, true, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     * @param callback 回调函数
     */
    public Handler(String rid, boolean isAsync, LiveRoomCallback.HuYa callback) {
        this(rid, isAsync, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, LiveRoomCodec<Message> codec, LiveRoomCallback.HuYa callback) {
        this(rid, true, codec, callback);
    }

    /**
     * 构造方法初始化
     * @param rid 房间 ID
     * @param isAsync 是否异步执行
     * @param codec 编解码器
     * @param callback 回调函数
     */
    public Handler(String rid, boolean isAsync, LiveRoomCodec<Message> codec, LiveRoomCallback.HuYa callback) {
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
        this.clientHuYaIntensifier = intensifier;
    }

    @Override
    public void onOpen(P6eWebSocketClient client) {
        try {
            // 创建客户端对象
            this.clientHuYa = new Client(rid, codec, client);
            // 增强客户端对象
            if (this.clientHuYaIntensifier != null) {
                this.clientHuYa = this.clientHuYaIntensifier.enhance(this.clientHuYa);
            }
            // 发送监听弹幕推送的消息
            this.clientHuYa.monitorEvent();

            // 心跳任务创建
            // 心跳任务如果存在将关闭
            if (this.task != null) {
                final String tid = this.task.getId();
                LOGGER.warn("[ HuYa " + this.rid + " ] instance has a previous task [ " + tid + " ]!!");
                LOGGER.warn("[ HuYa " + this.rid + " ] now execute to close task [ " + tid + " ]...");
                LOGGER.info("[ HuYa: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ HuYa: " + this.rid + " ] end closing task [ " + tid + " ].");
                LOGGER.warn("[ HuYa " + this.rid + " ] closing successful [ " + tid + " ].");
            }
            new LiveRoomApplication.Task(60, 60, true) {
                @Override
                public void execute() {
                    // 发送监听弹幕推送的消息
                    clientHuYa.monitorEvent();
                }
            };

            // 触发回调函数
            this.callback.onOpen(this.clientHuYa);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onClose(P6eWebSocketClient client) {
        try {
            this.callback.onClose(this.clientHuYa);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onClose ==> " + e.getMessage());
        } finally {
            if (this.task == null) {
                LOGGER.info("[ HuYa: " + this.rid + " ] no started task.");
            } else {
                final String tid = this.task.getId();
                LOGGER.info("[ HuYa: " + this.rid + " ] start closing task [ " + tid + " ].");
                this.task.close();
                this.task = null;
                LOGGER.info("[ HuYa: " + this.rid + " ] end closing task [ " + tid + " ].");
            }
        }
    }

    @Override
    public void onError(P6eWebSocketClient client, Throwable throwable) {
        try {
            this.callback.onError(this.clientHuYa, throwable);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onMessageText(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessageText ==> "
                    + new String(bytes, StandardCharsets.UTF_8)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessageText ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            LOGGER.info("收到的消息内容 ：  " +  byteBuf.readableBytes());
            this.callback.onMessage(this.clientHuYa, this.codec.decode(byteBuf));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessageBinary ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePong(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            client.sendMessagePing();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessagePong ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessagePing(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            client.sendMessagePong();
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessagePing ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.rid + " ] onMessageContinuation ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }
}
