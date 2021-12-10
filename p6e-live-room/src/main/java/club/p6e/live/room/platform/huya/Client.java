package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.live.room.utils.Utils;
import club.p6e.websocket.client.P6eWebSocketClient;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 *
 * 虎牙客户端
 *
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    /**
     * 虎牙: https://www.huya.com/
     * 开源项目地址: http://live.p6e.club/
     * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
     *
     * 虎牙客户端增强器
     *
     * @author lidashuang
     * @version 1.0
     */
    public interface Intensifier {
        /**
         * 增强原本的客户端对象
         * @param client 客户端对象
         * @return 客户端对象
         */
        public Client enhance(Client client);
    }

    /** RID */
    private final String rid;
    /** 编解码器 */
    private final LiveRoomCodec<Message> codec;
    /** 客户端 */
    private final P6eWebSocketClient p6eWebSocketClient;

    /**
     * 构造方法初始化
     * @param rid RID
     * @param p6eWebSocketClient WebSocket 客户端对象
     */
    public Client(String rid, LiveRoomCodec<Message> codec, P6eWebSocketClient p6eWebSocketClient) {
        this.rid = rid;
        this.codec = codec;
        this.p6eWebSocketClient = p6eWebSocketClient;
    }

    /**
     * 发送消息
     */
    public void sendMessage(Message message) {
        this.p6eWebSocketClient.sendMessageBinary(this.codec.encode(message));
    }

    /**
     *
     */
    public void monitorEvent() {
        // 6501 礼物
        // 1400 弹幕
        final Template<BarrageEventTemplate> template = new Template<>();
        template.setType(16);
        template.setData(new BarrageEventTemplate(this.rid, this.rid));
        final Message message = new Message();
        message.setData(template.toList());
        this.sendMessage(message);
    }

    public P6eWebSocketClient getP6eWebSocketClient() {
        return p6eWebSocketClient;
    }

    public static class Template<T> {
        private int type = 3;
        private T data;
        private Object unknown2;
        private String unknown3 = "";
        private Object unknown4;
        private Object unknown5;
        private String unknown6 = "";

        public Template() {}

        public Template(T data) {
            this.data = data;
        }

        public Template(int type, T data) {
            this.type = type;
            this.data = data;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Object getUnknown2() {
            return unknown2;
        }

        public void setUnknown2(Object unknown2) {
            this.unknown2 = unknown2;
        }

        public String getUnknown3() {
            return unknown3;
        }

        public void setUnknown3(String unknown3) {
            this.unknown3 = unknown3;
        }

        public Object getUnknown4() {
            return unknown4;
        }

        public void setUnknown4(Object unknown4) {
            this.unknown4 = unknown4;
        }

        public Object getUnknown5() {
            return unknown5;
        }

        public void setUnknown5(Object unknown5) {
            this.unknown5 = unknown5;
        }

        public String getUnknown6() {
            return unknown6;
        }

        public void setUnknown6(String unknown6) {
            this.unknown6 = unknown6;
        }

        public List<Object> toList() {
            final List<Object> list = new ArrayList<>();
            list.add(type);
            list.add(data);
            list.add(unknown2);
            list.add(unknown3);
            list.add(unknown4);
            list.add(unknown5);
            list.add(unknown6);
            return list;
        }
    }

    public static class BarrageEventTemplate {
        private List<String> list;
        private String unknown1 = "";

        public BarrageEventTemplate() {}

        public BarrageEventTemplate(String liveId, String chatId) {
            final List<String> list = new ArrayList<>();
            list.add("live:" + liveId);
            list.add("chat:" + chatId);
            this.list = list;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public String getUnknown1() {
            return unknown1;
        }

        public void setUnknown1(String unknown1) {
            this.unknown1 = unknown1;
        }
    }
}
