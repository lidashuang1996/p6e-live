package club.p6e.live.room.platform.huya;

import club.p6e.live.room.LiveRoomCodec;
import club.p6e.websocket.client.P6eWebSocketClient;

import java.security.MessageDigest;
import java.util.*;

/**
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 * <p>
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
     * <p>
     * 虎牙客户端增强器
     *
     * @author lidashuang
     * @version 1.0
     */
    public interface Intensifier {
        /**
         * 增强原本的客户端对象
         *
         * @param client 客户端对象
         * @return 客户端对象
         */
        public Client enhance(Client client);
    }

    /**
     * RID
     */
    private final String liveChannelId;
    /**
     * 编解码器
     */
    private final LiveRoomCodec<Message> codec;
    /**
     * 客户端
     */
    private final P6eWebSocketClient p6eWebSocketClient;

    /**
     * 构造方法初始化
     *
     * @param liveChannelId      LiveChannelId
     * @param p6eWebSocketClient WebSocket 客户端对象
     */
    public Client(String liveChannelId, LiveRoomCodec<Message> codec, P6eWebSocketClient p6eWebSocketClient) {
        this.liveChannelId = liveChannelId;
        this.codec = codec;
        this.p6eWebSocketClient = p6eWebSocketClient;
    }

    public String getLiveChannelId() {
        return liveChannelId;
    }

    /**
     * 发送消息
     */
    public void sendMessage(Message message) {
        this.p6eWebSocketClient.sendMessageBinary(this.codec.encode(message));
    }

    /**
     * 获取源的客户端对象
     *
     * @return 源的客户端对象
     */
    public P6eWebSocketClient getP6eWebSocketClient() {
        return p6eWebSocketClient;
    }

    /**
     * 发送监听房间的初始化消息
     * 6501 礼物
     * 1400 弹幕
     */
    @SuppressWarnings("ALL")
    public void sendLoginMessage() {
        final Map<String, Object> req = new HashMap<>(16);
        final Properties d = new Properties();
        final Properties d1 = new Properties();
        final Properties dr = new Properties();
        final Properties p1 = new Properties();
        final Properties p2 = new Properties();
        final Properties p3 = new Properties();
        p1.put("0", p2);
        p2.put("0", p3);
        p2.put("1", Void.TYPE);
        p2.put("2", Void.TYPE);
        p2.put("3", System.currentTimeMillis() - 16 * 3600 * 24 * 1000 * 365L);
        p2.put("4", "index/jdt/3");
        p2.put("5", "");
        p2.put("6", Void.TYPE);
        p2.put("7", Void.TYPE);
        p2.put("8", 1);
        p3.put("0", Void.TYPE);
        p3.put("1", "0adbb0c67a58a56545010ccc0dcc43ef");
        p3.put("2", "");
        p3.put("3", "webh5&2401311422&websocket");
        p3.put("4", "__yamid_new=CA9769471F1000017F4318F21EC0D880; game_did=fNdCvIVYyvFWUSbvXgDpZmwuK7DgrhXa7bk; __yamid_tt1=0.15956466044732132; guid=0adbb0c67a58a56545010ccc0dcc43ef; sdidshorttest=test; SoundValue=0.50; alphaValue=0.80; udb_guiddata=000593572de04b0bbad7fc9e05aa7406; guid=0adbb0c67a58a56545010ccc0dcc43ef; udb_deviceid=w_799429769157591040; udb_passdata=3; __yasmid=0.15956466044732132; Hm_lvt_51700b6c722f5bb4cf39906a596ea41f=1706436916,1706794702,1706801801,1707380070; _yasids=__rootsid%3DCA9F07B34B5000015AB213F058801EB7; huya_hd_rep_cnt=11; sdid=0UnHUgv0_qmfD4KAKlwzhqcfkLXakcmA4IGeJ2PRisbeaKfx-qgQ1X1ugt2iKp3kHr9sQRB25VJcZ38T7QIVx7U-H6d8Z-_Jo-whLYY_hqhvWVkn9LtfFJw_Qo4kgKr8OZHDqNnuwg612sGyflFn1dmS1k3FIplGiuRBo7OiPL6ZqWGV9yZDNxrzqJHxSRC3i; sdidtest=0UnHUgv0_qmfD4KAKlwzhqcfkLXakcmA4IGeJ2PRisbeaKfx-qgQ1X1ugt2iKp3kHr9sQRB25VJcZ38T7QIVx7U-H6d8Z-_Jo-whLYY_hqhvWVkn9LtfFJw_Qo4kgKr8OZHDqNnuwg612sGyflFn1dmS1k3FIplGiuRBo7OiPL6ZqWGV9yZDNxrzqJHxSRC3i; _rep_cnt=6; Hm_lpvt_51700b6c722f5bb4cf39906a596ea41f=1707398563; isInLiveRoom=true; huya_flash_rep_cnt=42; huya_web_rep_cnt=173; huya_ua=webh5&0.1.0&websocket");
        p3.put("5", Void.TYPE);
        p3.put("6", "chrome");
        req.put("tReq", p1);
        dr.put("0", req);
        d1.put("1", 3);
        d1.put("2", Void.TYPE);
        d1.put("3", Void.TYPE);
        d1.put("4", 1);
        d1.put("5", "huyaliveui");
        d1.put("6", "getLivingInfo");
        d1.put("7", dr);
        d1.put("8", Void.TYPE);
        d1.put("9", new HashMap<>(0));
        d1.put("10", new HashMap<>(0));
        final String d3 = execute1();
        final byte[] bytes = new Taf.Encoder(d1).execute();
        final int length = bytes.length + 4;
        final byte[] result = new byte[length];
        result[0] = (byte) ((length >> 24) & 0xFF);
        result[1] = (byte) ((length >> 16) & 0xFF);
        result[2] = (byte) ((length >> 8) & 0xFF);
        result[3] = (byte) (length & 0xFF);
        System.arraycopy(bytes, 0, result, 4, bytes.length);
        d.put("0", 3);
        d.put("1", d1);
        d.put("2", Void.TYPE);
        d.put("3", d3 + ":" + d3 + ":0:0");
        d.put("4", Void.TYPE);
        d.put("5", Void.TYPE);
        d.put("6", execute2(result));
        final Message message = new Message();
        message.setData(d);
        this.sendMessage(message);
    }

    public void sendDataMessage() {
        final Properties d = new Properties();
        final Properties d1 = new Properties();
        final List<String> list = new ArrayList<>();
        list.add("live:" + liveChannelId);
        list.add("chat:" + liveChannelId);
        d1.put("0", list);
        d1.put("1", "");
        d.put("0", 16);
        d.put("1", d1);
        d.put("2", 1);
        d.put("3", "");
        d.put("4", Void.TYPE);
        d.put("5", Void.TYPE);
        d.put("6", "");
        final Message message = new Message();
        message.setData(d);
        message.put("$IS_LENGTH_MARK", false);
        this.sendMessage(message);
    }


    @SuppressWarnings("ALL")
    private String execute1() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 16);
    }

    @SuppressWarnings("ALL")
    private String execute2(byte[] bytes) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] result = md.digest(bytes);
            final StringBuilder sb = new StringBuilder();
            for (final byte r : result) {
                final String hex = Integer.toHexString(0xFF & r);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
