//package club.p6e.live.room.platform.egame;
//
//import club.p6e.live.room.utils.Utils;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
//import java.lang.reflect.Field;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public final class Taf {
//
//    /**
//     * 解码
//     */
//    public static class Decode {
//        /** 数据栈 */
//        private final Stack<Map<Integer, Object>> stack = new Stack<>();
//        private final Stack<Map<Integer, Object>> cacheStack = new Stack<>();
//
//        /**
//         * 解析数据
//         * @param input 输入流
//         * @return 结果
//         */
//        public Map<Integer, Object> execute(ByteBuf input) {
//            final Map<Integer, Object> result = new HashMap<>(16);
//            while (input.isReadable()) {
//                final int[] info = getInfo(input);
//                if (info[1] == 10) {
//                    final Map<Integer, Object> custom = stack.push(new HashMap<>(16));
//                    result.put(info[0], custom);
//                    continue;
//                }
//                if (info[1] == 11) {
//                    if (!stack.empty()) {
//                        stack.pop();
//                    }
//                    continue;
//                }
//                if (stack.empty()) {
//                    result.put(info[0], getData(info[1], input));
//                } else {
//                    stack.peek().put(info[0], getData(info[1], input));
//                }
//            }
//            return result;
//        }
//
//        /**
//         * 获取当前的索引
//         * @param input 输入流对象
//         * @return 索引结果为数组，长度为 2. [0] ==> 索引（TAG） [1] ==> 类型
//         */
//        public int[] getInfo(ByteBuf input) {
//            final int data = input.readByte();
//            final int type = data & 0x0F;
//            final int tag = data >> 4 & 0x0F;
//            return tag >= 0x0F ? new int[] { (int) input.readByte(), type } : new int[] { tag, type };
//        }
//
//        /**
//         * 根据类型读取数据
//         * @param type 类型
//         * @param input 输入流对象
//         * @return 根据类型读取数据对象
//         */
//        public Object getData(int type, ByteBuf input) {
//            return getData(type, input, false);
//        }
//        public Object getData(int type, ByteBuf input, boolean is) {
//            Object o;
//            switch (type) {
//                case 0:
//                    o = getInt1(input);
//                    break;
//                case 1:
//                    o = getInt2(input);
//                    break;
//                case 2:
//                    o = getInt4(input);
//                    break;
//                case 3:
//                    o = getInt8(input);
//                    break;
//                case 4:
//                    o = getFloat(input);
//                    break;
//                case 5:
//                    o = getDouble(input);
//                    break;
//                case 6:
//                    o = getString1(input, is);
//                    break;
//                case 7:
//                    o = getString4(input, is);
//                    break;
//                case 8:
//                    o = getMap(input);
//                    break;
//                case 9:
//                    o = getList(input);
//                    break;
//                case 10:
//                case 11:
//                    cacheStack.push(new HashMap<>(16));
//                    while (input.isReadable()) {
//                        final int[] info = getInfo(input);
//                        if (info[1] == 11) {
//                            return cacheStack.pop();
//                        }
//                        cacheStack.peek().put(info[0], getData(info[1], input));
//                    }
//                    throw new RuntimeException("type 10/11 resolve exceptions.");
//                case 12:
//                    o = getNull();
//                    break;
//                case 13:
//                    o = getSimpleList(input);
//                    break;
//                default:
//                    throw new RuntimeException("get data type error.");
//            }
//            return o;
//        }
//
//        /**
//         * 读取长度
//         * @param input 输入流对象
//         * @return 长度内容
//         */
//        public int getLengthType(ByteBuf input) {
//            final int lenType = getInt1(input);
//            switch (lenType) {
//                case 0:
//                    return getInt1(input);
//                case 1:
//                    return getInt2(input);
//                case 2:
//                    return getInt4(input);
//                case 12:
//                    return -1;
//                default:
//                    throw new RuntimeException("length type error.");
//            }
//        }
//
//        /**
//         * 获取1个字节整型数据  [0]
//         * @param input 输入流对象
//         * @return 1个字节整型数据
//         */
//        public int getInt1(ByteBuf input) {
//            return input.readByte() & 0xFF;
//        }
//
//        /**
//         * 获取2个字节整型数据 [1]
//         * @param input 输入流对象
//         * @return 2个字节整型数据
//         */
//        public int getInt2(ByteBuf input) {
//            return ((input.readByte() & 0xFF) << 8) | (input.readByte() & 0xFF);
//        }
//
//        /**
//         * 获取4个字节整型数据 [2]
//         * @param input 输入流对象
//         * @return 4个字节整型数据
//         */
//        public int getInt4(ByteBuf input) {
//            return input.readInt();
//        }
//
//        /**
//         * 获取8个字节整型数据 [3]
//         * @param input 输入流对象
//         * @return 8个字节整型数据
//         */
//        public long getInt8(ByteBuf input) {
//            return input.readLong();
//        }
//
//        /**
//         * 获取4个字节Float [4]
//         * @param input 输入流对象
//         * @return 4个字节Float
//         */
//        public float getFloat(ByteBuf input) {
//            return input.readFloat();
//        }
//
//        /**
//         * 获取8个字节Double [5]
//         * @param input 输入流对象
//         * @return 8个字节Double
//         */
//        public double getDouble(ByteBuf input) {
//            return input.readDouble();
//        }
//
//        /**
//         * 获取字符串 [6]
//         * @param input 输入流对象
//         * @return 字符串
//         */
//        public Object getString1(ByteBuf input) {
//            return getString1(input, false);
//        }
//        public Object getString1(ByteBuf input, boolean is) {
//            int len = getInt1(input);
//            if (is) {
//                if (len > 0) {
//                    ByteBuf byteBuf = null;
//                    try {
//                        byteBuf = input.readBytes(len);
//                        return execute(byteBuf);
//                    } finally {
//                        if (byteBuf != null) {
//                            byteBuf.release();
//                        }
//                    }
//                } else {
//                    throw new RuntimeException("get string1 len <= 0");
//                }
//            } else {
//                final byte[] bytes = new byte[len];
//                input.readBytes(bytes);
//                return new String(bytes);
//            }
//        }
//
//        /**
//         * 获取字符串 [7]
//         * @param input 输入流对象
//         * @return 字符串
//         */
//        public Object getString4(ByteBuf input) {
//            return getString4(input, false);
//        }
//        public Object getString4(ByteBuf input, boolean is) {
//            int len = getInt4(input);
//            if (is) {
//                len = len - 2;
//                if (len > 0) {
//                    return execute(input.readBytes(len));
//                } else {
//                    throw new RuntimeException("get string2 len <= 0");
//                }
//            } else {
//                final byte[] bytes = new byte[len];
//                input.readBytes(bytes);
//                return new String(bytes);
//            }
//        }
//
//        /**
//         * 获取 Map 紧跟一个整型数据表示Map的大小，再跟[key, value]对列表 [8]
//         * @param input 输入流对象
//         * @return Map
//         */
//        public Map<Object, Object> getMap(ByteBuf input) {
//            // 代表的长度为 len
//            final int len = getLengthType(input);
//            if (len < 0) {
//                return null;
//            }
//            final Map<Object, Object> result = new HashMap<>(len);
//            for (int i = 0; i < len; i++) {
//                result.put(getMapKey(input), getMapValue(input));
//            }
//            return result;
//        }
//
//        public Object getMapKey(ByteBuf input) {
//            final int[] info = getInfo(input);
//            if (info[0] == 0) {
//                return getData(info[1], input);
//            }
//            throw new RuntimeException("map key error.");
//        }
//
//        public Object getMapValue(ByteBuf input) {
//            final int[] info = getInfo(input);
//            if (info[0] == 1) {
//                return getData(info[1], input);
//            }
//            throw new RuntimeException("map value error.");
//        }
//
//        /**
//         * 获取 List 紧跟一个整型数据表示List的大小，再跟元素列表 [9]
//         * @param input 输入流对象
//         * @return List
//         */
//        public List<Object> getList(ByteBuf input) {
//            int len = getLengthType(input);
//            if (len < 0) {
//                return null;
//            }
//            final List<Object> result = new ArrayList<>();
//            for (int i = 0; i < len; i++) {
//                result.add(getListValue(input));
//            }
//            return result;
//        }
//
//        public Object getListValue(ByteBuf input) {
//            final int[] info = getInfo(input);
//            if (info[0] == 0) {
//                return getData(info[1], input, true);
//            }
//            throw new RuntimeException("list value error.");
//        }
//
//        /**
//         * 后面不跟数据  [12]
//         * @return null
//         */
//        public Object getNull() {
//            return null;
//        }
//
//        /**
//         * 获取 SimpleList 简单列表（目前用在byte数组） [13]
//         * 紧跟一个类型字段（目前只支持byte）
//         * 紧跟一个整型数据表示长度，再跟byte数据
//         * @param input 输入流对象
//         * @return Map<Integer, Object> 解码后的数据对象
//         */
//        public Object getSimpleList(ByteBuf input) {
//            final int type = getInt1(input);
//            if (type == 0) {
//                final int len = getLengthType(input);
//                final ByteBuf byteBuf = input.readBytes(len);
//                try {
//                    final byte[] bytes = new byte[byteBuf.readerIndex()];
//                    byteBuf.readBytes(bytes);
//                    return bytes;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return new HashMap<>();
//                } finally {
//                    byteBuf.release();
//                }
//            }
//            throw new RuntimeException("simple list type error.");
//        }
//
//        public void cleanStack() {
//            stack.clear();
//            cacheStack.clear();
//        }
//    }
//
//    /**
//     * 编码
//     */
//    public static class Encode {
//
//        private boolean bool;
//
//        public ByteBuf execute(List<Object> list) {
//            return execute(list, false);
//        }
//
//        public ByteBuf execute(List<Object> list, boolean bool) {
//            this.bool = bool;
//            final ByteBuf byteBuf = Unpooled.buffer();
//            for (int i = 0; i < list.size(); i++) {
//                setData(byteBuf, i, list.get(i));
//            }
//            return byteBuf;
//        }
//
//        @SuppressWarnings("all")
//        public void setData(ByteBuf out, int tag, Object o) {
//            if (o == null) {
//                setInfo(out, tag, 12);
//            } else {
//                if (o instanceof Integer) {
//                    final int oi = (int) o;
//                    if (oi < 0xFF) {
//                        setInfo(out, tag, 0);
//                        out.writeByte((oi & 0xFF));
//                    } else if (oi < 0xFFFF) {
//                        setInfo(out, tag, 1);
//                        out.writeByte(((oi >> 8) & 0xFF));
//                        out.writeByte((oi & 0xFF));
//                    } else if (oi < Integer.MAX_VALUE ) {
//                        setInfo(out, tag, 2);
//                        out.writeInt(oi);
//                    } else {
//                        throw new RuntimeException("object to integer error.");
//                    }
//                } else if (o instanceof Long) {
//                    final long ol = (long) o;
//                    setInfo(out, tag, 3);
//                    out.writeLong(ol);
//                } else if (o instanceof Float) {
//                    final float of = (float) o;
//                    setInfo(out, tag, 4);
//                    out.writeFloat(of);
//                } else if (o instanceof Double) {
//                    final double od = (double) o;
//                    setInfo(out, tag, 5);
//                    out.writeDouble(od);
//                } else if (o instanceof String) {
//                    final String os = (String) o;
//                    final int len = os.length();
//                    if (len < 0xFF) {
//                        setInfo(out, tag, 6);
//                        out.writeByte(len);
//                    } else {
//                        setInfo(out, tag, 7);
//                        out.writeInt(len);
//                    }
//                    out.writeBytes(os.getBytes(StandardCharsets.UTF_8));
//                } else if (o instanceof Map) {
//                    final Map<Object, Object> om = (Map<Object, Object>) o;
//                    setInfo(out, tag, 8);
//                    if (om.size() == 0) {
//                        setNullType(out);
//                    } else {
//                        setLengthType(out, om.size());
//                        for (Object key : om.keySet()) {
//                            setMapKey(out, key);
//                            setMapValue(out, om.get(key));
//                        }
//                    }
//                } else if (o instanceof List) {
//                    final List<Object> ol = (List<Object>) o;
//                    setInfo(out, tag, 9);
//                    if (ol.size() == 0) {
//                        setNullType(out);
//                    } else {
//                        setLengthType(out, ol.size());
//                        for (Object item : ol) {
//                            setListValue(out, item);
//                        }
//                    }
//                } else {
//                    final boolean b = bool;
//                    final Class<?> clazz = o.getClass();
//                    final Field[] fields = clazz.getDeclaredFields();
//                    final List<Object> list = new ArrayList<>();
//                    if (b) {
//                        // 预留长度位置
//                        list.add(null);
//                    }
//                    for (final Field field : fields) {
//                        try {
//                            field.setAccessible(true);
//                            list.add(field.get(o));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    ByteBuf byteBuf = null;
//                    try {
//                        byteBuf = execute(list);
//                        final int len = byteBuf.readableBytes();
//                        setInfo(out, tag, 13);
//                        out.writeByte(0);
//                        if (b) {
//                            // 写入长度
//                            byteBuf.readByte();
//                            setLengthType(out, len - 1);
//                            out.writeInt(len - 1);
//                            while (byteBuf.isReadable()) {
//                                out.writeByte(byteBuf.readByte());
//                            }
//                        } else {
//                            setLengthType(out, len);
//                            out.writeBytes(byteBuf);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (byteBuf != null) {
//                            byteBuf.release();
//                        }
//                    }
//                }
//                // 自定义的字段 // 10  11
//                // 暂不实现
//            }
//        }
//
//        public void setInfo(ByteBuf out, int tag, int type) {
//            if (tag <= 0x0F) {
//                int d = ((tag & 0x0F) << 4) | (type & 0x0F);
//                out.writeByte(d);
//            } else {
//                out.writeByte((type & 0x0F));
//                out.writeByte((tag & 0xFF));
//            }
//        }
//
//        public void setMapKey(ByteBuf out, Object v) {
//            setData(out, 0, v);
//        }
//
//        public void setMapValue(ByteBuf out, Object v) {
//            setData(out, 1, v);
//        }
//
//        public void setListValue(ByteBuf out, Object v) {
//            setData(out, 0, v);
//        }
//
//        public void setNullType(ByteBuf out) {
//            out.writeByte(12);
//        }
//        public void setLengthType(ByteBuf out, long v) {
//            if (v < 0xFF) {
//                out.writeByte(0);
//                out.writeByte(((int)v & 0xFF));
//            } else if (v < 0xFFFF) {
//                out.writeByte(1);
//                out.writeByte((((int)v >> 8) & 0xFF));
//                out.writeByte(((int)v & 0xFF));
//            } else if (v < Integer.MAX_VALUE ) {
//                out.writeByte(2);
//                out.writeInt((int) v);
//            } else {
//                out.writeByte(3);
//                out.writeLong(v);
//            }
//        }
//    }
//}
