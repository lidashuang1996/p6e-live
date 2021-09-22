package club.p6e.live.room.platform.douyin;

import club.p6e.live.room.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Decoder {

    /** 平台名称 */
    private static final String PLATFORM = "DOU_YIN";
    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);

    /**
     * 解码操作
     * @param in 输入的对象
     * @return 输出的对象
     */
    public Message decode(ByteBuf in) {
        // 解码的内容长度
        LOGGER.debug("[ " + PLATFORM + " ] decode content length ==> " + in.readableBytes());
        // 解码且将返回的对象添加到管道中
        final Message message = new Message();
        message.putAll(decodeByteBufToMessages(in));
        return message;
    }

    public static Map<Integer, Object> decodeByteBufToMessages(ByteBuf byteBuf) {
        final byte[] ts = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(ts);
        byteBuf.resetReaderIndex();
        final Map<Integer, Object> result = new HashMap<>(16);
        while (true) {
            final int[] tt = getTagAndType(byteBuf);
            if (tt == null) {
                break;
            }
            switch (tt[1]) {
                case 0:
                    result.put(tt[0], getType0(byteBuf));
                    break;
                case 1:
                    result.put(tt[0], getType1(byteBuf));
                    break;
                case 2:
                    final ByteBuf buf = getType2(byteBuf);
                    try {
                        final int[] tt2 = getTagAndType(buf);
                        buf.resetReaderIndex();
                        if (tt2 != null && tt2[0] == 1) {
                            mapWriteData(result, tt[0], decodeByteBufToMessages(buf));
                        } else {
                            mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
                        }
                    } finally {
                        buf.release();
                    }
                    break;
                default:
                    final byte[] readBytes = new byte[byteBuf.readerIndex()];
                    byteBuf.readBytes(readBytes);
                    throw new RuntimeException("decodeByteBufToMessages type is null." + Utils.bytesToHex(readBytes) + "  \n\n " + result);
            }
        }
        return result;
    }

    private static void mapWriteData(Map<Integer, Object> m, Integer a, Object b) {
        if (a == 1 && b instanceof String) {
            String t = Utils.bytesToHex(String.valueOf(b).getBytes(StandardCharsets.UTF_8));
            if (t.startsWith("22060a0408001200")) {
                return;
            }
        }
//        if (a == 2 && b instanceof String) {
//            b = ts2((String)b);
//        }
        if (a == 23 && b instanceof String) {
             b = ts1((String) b); // 特殊处理
        }
        if (m.get(a) == null) {
            m.put(a, b);
        } else {
            if (m.get(a) instanceof List) {
                ((List) m.get(a)).add(b);
            } else {
                List list = new ArrayList();
                list.add(b);
                m.put(a, list);
            }
        }
    }


    public static int[] getTagAndType(ByteBuf byteBuf) {
        long data;
        try {
            data = getType0(byteBuf);
        } catch (Exception e) {
            return null;
        }
        if (data < 0) {
            return null;
        }
        return new int[] { (int) data >> 3, (int) data & 0x07 };
    }

    public static long getType0(ByteBuf byteBuf) {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            if (byteBuf.isReadable()) {
                String ls = "00000000" + Integer.toBinaryString(byteBuf.readByte());
                ls = ls.substring(ls.length() - 8);
                sb.insert(0, ls.substring(1));
                if (ls.startsWith("0")) {
                    break;
                }
            } else {
                return -1;
            }
        }
        if (sb.toString().length() == 70
                && sb.toString().startsWith("0000001111111111111111111111111111111111111111111111111111111111111111")) {
            return -1;
        } else {
            return Long.parseLong(sb.toString(), 2);
        }
    }

    public static double getType1(ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }

    public static ByteBuf getType2(ByteBuf byteBuf) {
        final long len = getType0(byteBuf);
        if (len > 0) {
            return byteBuf.readBytes((int) len);
        } else {
            return Unpooled.buffer();
        }
    }


    public static Object ts1(String content) {
        final byte[] bytes = String.valueOf(content).getBytes(StandardCharsets.UTF_8);
        String hexContent = Utils.bytesToHex(bytes);
        System.out.println(hexContent);
        return content;
//        if (hexContent.startsWith("30") && hexContent.endsWith("0c200c3001")) {
//            Map<Integer, Object> map = new HashMap<>();
//            hexContent = hexContent.substring(4, hexContent.length() - 10);
//            int indexStart = hexContent.indexOf("efbfbd01efbfbd02");
//            int indexEnd = hexContent.lastIndexOf("efbfbd01efbfbd02");
//            if (indexEnd > indexStart) {
//                String a = null, b = null;
//                try {
//                    a = hexContent.substring(indexStart + 16, indexEnd);
//                    b = hexContent.substring(indexEnd + 16);
//                    // zhixing 1
//                    mapWriteData(map, 1, ex2(Utils.hexToByte(a)));
//                    // zhixing 2
//                    mapWriteData(map, 2, ex2(Utils.hexToByte(b)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return map;
//            }
//        }
//        return content;
    }


//    public static Object ts2(String content) {
//        final byte[] bytes = String.valueOf(content).getBytes(StandardCharsets.UTF_8);
//        String hexContent = Utils.bytesToHex(bytes);
//        Map<Integer, Object> map = new HashMap<>();
//        final String[] hexContents = hexContent.split("efbfbd");
//
//        for (int i = 0; i < hexContents.length; i++) {
//            if (hexContents[i].startsWith("02")) {
//                try {
//                    map.put(i + 1, ex2(Utils.hexToByte(hexContents[i].substring(2))));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return map;
//    }

    public static void main(String[] args) {
        String a = "0a8d030a1257656263617374526f6f6d4d65737361676512ec020a2f0a1257656263617374526f6f6d4d657373616765108080f7e8a588aca46118a4969fa0e9f693a46120d486c29fc02f12b802e6aca2e8bf8ee69da5e588b0e79bb4e692ade997b4efbc81e68a96e99fb3e4b8a5e7a681e69caae68890e5b9b4e4babae79bb4e692ade68896e68993e8b58fe38082e79bb4e692ade997b4e58685e4b8a5e7a681e587bae78eb0e8bf9de6b395e8bf9de8a784e38081e4bd8ee4bf97e889b2e68385e38081e590b8e7839fe98597e98592e7ad89e58685e5aeb9e38082e5a682e4b8bbe692ade59ca8e79bb4e692ade8bf87e7a88be4b8ade4bba5e4b88de5bd93e696b9e5bc8fe8afb1e5afbce68993e8b58fe38081e7a781e4b88be4baa4e69893efbc8ce8afb7e8b0a8e6858ee588a4e696adefbc8ce4bba5e998b2e4babae8baabe8b4a2e4baa7e68d9fe5a4b1e38082e8afb7e5a4a7e5aeb6e6b3a8e6848fe8b4a2e4baa7e5ae89e585a8efbc8ce8b0a8e998b2e7bd91e7bb9ce8af88e9aa97e38082188080f7e8a588aca4610ad00a0a1757656263617374526f6f6d496e74726f4d65737361676512aa0a0a250a1757656263617374526f6f6d496e74726f4d657373616765109ea88098edc2b8b160300112eb0808cf8ea0a1c78cb00210a1d9c0be0f1a09e5b88ce8939de8939d20014aff020a6b68747470733a2f2f70332e646f7579696e7069632e636f6d2f696d672f746f732d636e2d692d303831332f66383433356336346635666434633939396662653438646466613462396334307e63355f313030783130302e6a7065673f66726f6d3d343031303533313033380a6b68747470733a2f2f70362e646f7579696e7069632e636f6d2f696d672f746f732d636e2d692d303831332f66383433356336346635666434633939396662653438646466613462396334307e63355f313030783130302e6a7065673f66726f6d3d343031303533313033380a6b68747470733a2f2f70392e646f7579696e7069632e636f6d2f696d672f746f732d636e2d692d303831332f66383433356336346635666434633939396662653438646466613462396334307e63355f313030783130302e6a7065673f66726f6d3d343031303533313033381236313030783130302f746f732d636e2d692d303831332f6638343335633634663566643463393939666265343864646661346239633430aa01af020a55687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167651222776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e67181020203001b20105081f10e45c820200b202074c6d3932313131f2024c4d5334774c6a414241414141774942446b445641784a4478794764516c375065776e5f783362536f714863734d7341705a48654b57716879535f6a34747a7837475736445956624753507047b00303ea03af020a55687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e677e74706c762d6f626a2e696d6167651222776562636173742f757365725f67726164655f6c6576656c5f76355f32342e706e671810202030012273e6aca2e8bf8ee69da5e588b0e79bb4e692ade997b420e6849fe8b0a2e5a4a7e5aeb6e8a782e79c8b20e782b9e782b9e585b3e6b3a820e58aa0e7b289e4b89de59ba2e4baa4e69c8be58f8be8b0a2e8b0a2e5a4a7e5aeb620e58d95e59b9be4ba8ce68c87e6b497e5a4b4e689a3e5a4a7e588862a053930e5908e2a09e59ca8e5b9bfe5ae892a0ce59083e9b8a1e6898be6b8b8189ea88098edc2b8b1601213313633323135333636363338395f315f315f3118e80720d586c29fc02f2a5d66657463685f74696d653a313633323135333636363338397c73746172745f74696d653a307c666c61673a307c7365713a317c6e6578745f637572736f723a313633323135333636363338395f315f315f317c646f6f723a332d6e3435";
        System.out.println();
        System.out.println(Utils.toJson(decodeByteBufToMessages(Unpooled.copiedBuffer(Utils.hexToByte(a)))));
        // 3=6945362163015881758}], 2=1632153666389_1_1_1, 3=1000,
        // 4=1632153666389, 5=fetch_time:1632153666389|start_time:0|flag:0|seq:1|next_cursor:1632153666389_1_1_1|door:3-n45}
    }

}

//  https://live.douyin.com/webcast/im/fetch/?aid=6383
//  &live_id=1&device_platform=web&language=zh&room_id=7009940465588357924
//  &resp_content_type=protobuf&version_code=9999&identity=audience
//  &internal_ext=fetch_time:1632190108873|start_time:0|flag:0|seq:1|next_cursor:1632190108873_1_1_1|door:3-n45
//  &cursor=door:3-n45
//  &last_rtt=950 &did_rule=3
//
//  &_signature=_02B4Z6wo00d01ic9hyQAAIDD2XH7FMvs2zYnMYOAAOiU0LD47bVbYovGkSEpg5ZaV6snsnajyV7wcd1KkUKNZbliYBKln1wgGVPCoBqOPfwa1djx-RvRNyUlX3eDNY6iIkMu801jIX1jdOtO88
//onClose

// 30 02efbfbd 01efbfbd 02
// 0a54687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f322e706e677e74706c762d6f626a2e696d6167650a54687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f322e706e677e74706c762d6f626a2e696d6167650a54687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f322e706e677e74706c762d6f626a2e696d6167651219757365725f67726164655f6c6576656c5f76355f322e706e67181020203001efbfbd01efbfbd020a55687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f315f342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f315f342e706e677e74706c762d6f626a2e696d6167650a55687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f315f342e706e677e74706c762d6f626a2e696d616765121a6177656d655f7061795f67726164655f32785f315f342e706e67180c200c3001
