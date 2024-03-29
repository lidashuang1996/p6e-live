package club.p6e.live.room.platform.huya;

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
 * <p>
 * 虎牙连接处理器对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class Handler implements P6eWebSocketCallback {

    /**
     * 注入日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    /**
     * 房间 ID
     */
    private final String liveChannelId;
    /**
     * 是否异步
     */
    private final boolean isAsync;
    /**
     * 编码器
     */
    private final LiveRoomCodec<Message> codec;
    /**
     * 回调执行函数
     */
    private final LiveRoomCallback.HuYa callback;

    /**
     * 客户端
     */
    private Client clientHuYa;
    /**
     * 客户端增强器
     */
    private Client.Intensifier clientHuYaIntensifier;

    /**
     * 构造方法初始化
     *
     * @param liveChannelId 房间 ID
     * @param callback      回调函数
     */
    public Handler(String liveChannelId, LiveRoomCallback.HuYa callback) {
        this(liveChannelId, true, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     *
     * @param liveChannelId 房间 ID
     * @param isAsync       是否异步执行
     * @param callback      回调函数
     */
    public Handler(String liveChannelId, boolean isAsync, LiveRoomCallback.HuYa callback) {
        this(liveChannelId, isAsync, Application.getCodec(), callback);
    }

    /**
     * 构造方法初始化
     *
     * @param liveChannelId 房间 ID
     * @param codec         编解码器
     * @param callback      回调函数
     */
    public Handler(String liveChannelId, LiveRoomCodec<Message> codec, LiveRoomCallback.HuYa callback) {
        this(liveChannelId, true, codec, callback);
    }

    /**
     * 构造方法初始化
     *
     * @param liveChannelId 房间 ID
     * @param isAsync       是否异步执行
     * @param codec         编解码器
     * @param callback      回调函数
     */
    public Handler(String liveChannelId, boolean isAsync, LiveRoomCodec<Message> codec, LiveRoomCallback.HuYa callback) {
        this.liveChannelId = liveChannelId;
        this.isAsync = isAsync;
        this.codec = codec;
        this.callback = callback;
    }

    /**
     * 获取 liveChannelId
     *
     * @return liveChannelId
     */
    public String getLiveChannelId() {
        return liveChannelId;
    }

    /**
     * 是否异步执行
     *
     * @return 异步执行的状态
     */
    public boolean isAsync() {
        return isAsync;
    }

    /**
     * 设置客户端增强器
     *
     * @param intensifier 增强器对象
     */
    public void setClientIntensifier(Client.Intensifier intensifier) {
        this.clientHuYaIntensifier = intensifier;
    }

    @Override
    public void onOpen(P6eWebSocketClient client) {
        try {
            // 创建客户端对象
            this.clientHuYa = new Client(liveChannelId, codec, client);
            // 增强客户端对象
            if (this.clientHuYaIntensifier != null) {
                this.clientHuYa = this.clientHuYaIntensifier.enhance(this.clientHuYa);
            }
            // 发送监听弹幕推送的消息
            this.clientHuYa.sendLoginMessage();
            this.clientHuYa.sendDataMessage();
            // 触发回调函数
            this.callback.onOpen(this.clientHuYa);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onClose(P6eWebSocketClient client) {
        try {
            this.callback.onClose(this.clientHuYa);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onClose ==> " + e.getMessage());
        }
    }

    @Override
    public void onError(P6eWebSocketClient client, Throwable throwable) {
        try {
            this.callback.onError(this.clientHuYa, throwable);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onError ==> " + e.getMessage());
        }
    }

    @Override
    public void onMessageText(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessageText ==> "
                    + new String(bytes, StandardCharsets.UTF_8)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessageText ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            // 解码得到消息对象
            // 回调收到消息方法
            // LOGGER.info("收到的消息内容 ：  " +  byteBuf.readableBytes());
            this.callback.onMessage(this.clientHuYa, this.codec.decode(byteBuf));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessageBinary ==> " + e.getMessage());
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
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessagePong ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessagePong ==> " + e.getMessage());
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
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessagePing ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessagePing ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient client, ByteBuf byteBuf) {
        try {
            final byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessageContinuation ==> " + Arrays.toString(bytes)
                    + ", message format is incorrect and will be discarded.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ HuYa: " + this.liveChannelId + " ] onMessageContinuation ==> " + e.getMessage());
        } finally {
            byteBuf.release();
        }
    }
}
