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
    private static final int MARK = 48;

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
        message.putAll(decodeByteBufToMessage(in));
        return message;
    }

    @SuppressWarnings("all")
    private static Map<Integer, Object> decodeByteBufToMessage(ByteBuf byteBuf) {
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
                        if (buf == null) {
                            mapWriteData(result, tt[0], "");
                        } else if (buf.readableBytes() == 1) {
                            mapWriteData(result, tt[0], Utils.bytesToHex(buf.array()));
                        } else {
                            final int[] tt2 = getTagAndType(buf);
                            buf.resetReaderIndex();
                            if (tt2 == null) {
                                mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
                            } else {
                                if ((tt2[0] == 1 && (tt2[1] == 0 || tt2[1] == 1 || tt2[1] == 2))
                                        || (tt2[0] == 4 && tt2[1] == 2)
                                        || ((tt[0] == 2 || tt[0] == 21) && tt2[0] == 2 && tt2[1] == 2)) {
                                    mapWriteData(result, tt[0], decodeByteBufToMessage(buf));
                                } else {
                                    final int mark = buf.readByte();
                                    buf.resetReaderIndex();
                                    if (MARK == mark) {
                                        final Object o = tsFun(buf);
                                        if (o == null) {
                                            mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
                                        } else {
                                            mapWriteData(result, tt[0], o);
                                        }
                                    } else {
                                        mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
                                    }
                                }
                            }
                        }
                    } finally {
                        if (buf != null) {
                            buf.release();
                        }
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

    /**
     * 3038 3233 5f 6c 69 79 61 6f 79 61 6f
     * 写入数据
     * @param m
     * @param a
     * @param b
     */
    @SuppressWarnings("all")
    private static void mapWriteData(Map<Integer, Object> m, Integer a, Object b) {
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
            final ByteBuf buf = Unpooled.buffer((int) len);
            byteBuf.readBytes(buf);
            return buf;
        } else {
            return null;
        }
    }

    /**
     *
     *
     * 30
     * 15 efbfbd01 efbfbd02
     *
     * 0a 55 (85) 687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32312e706e677e74706c762d6f626a2e696d616765
     * 0a 55      687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32312e706e677e74706c762d6f626a2e696d616765
     * 0a 55      687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f757365725f67726164655f6c6576656c5f76355f32312e706e677e74706c762d6f626a2e696d616765
     *
     * 12 1a 757365725f67726164655f6c6576656c5f76355f32312e706e67
     * 18 10
     * 20 20
     * 30 01
     *
     * efbfbd01  efbfbd02
     * 0a 57 (87) 687474703a2f2f70392d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f32305f32342e706e677e74706c762d6f626a2e696d616765
     * 0a 57      687474703a2f2f70332d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f32305f32342e706e677e74706c762d6f626a2e696d616765
     * 0a 57      687474703a2f2f70362d776562636173742e646f7579696e7069632e636f6d2f696d672f776562636173742f6177656d655f7061795f67726164655f32785f32305f32342e706e677e74706c762d6f626a2e696d616765
     * 12 1c      6177656d655f7061795f67726164655f32785f32305f32342e706e67
     * 18 0c
     * 20 0c
     * 30 01
     *
     * 30 38 32 33 5f 6c 69 79 61 6f 79 61 6f
     *
     */
    public static Map<Integer, Object> tsFun(ByteBuf byteBuf) {
        final Map<Integer, Object> result = new HashMap<>(16);
        byteBuf.readByte();
        result.put(0, byteBuf.readByte());
        while (byteBuf.isReadable()) {
            final int key = (int) getType0(byteBuf);
            final int length = (int) getType0(byteBuf);
            if (key == -1 || length == -1 || length > byteBuf.readableBytes()) {
                return null;
            }
            final ByteBuf buf = Unpooled.buffer(length);
            try {
                byteBuf.readBytes(buf);
                result.put(key, decodeByteBufToMessage(buf));
            } finally {
                buf.release();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new String(Utils.hexToByte("303832335f6c6979616f79616f")));
    }
}
