package club.p6e.live.room.platform.huya;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 虎牙: https://www.huya.com/
 * 开源项目地址: http://live.p6e.club/
 * Github 项目地址 Github: https://github.com/lidashuang1996/p6e-live
 * <p>
 * TAF 编解码
 *
 * @author lidashuang
 * @version 1.0
 */
public final class Taf {

    /**
     * 解码
     */
    @SuppressWarnings("ALL")
    public static class Decoder {
        public static class InfoModel implements Serializable {
            private int tag;
            private int type;

            public InfoModel(int tag, int type) {
                this.tag = tag;
                this.type = type;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTag() {
                return tag;
            }

            public int getType() {
                return type;
            }
        }

        private final ByteBuf source;
        private final boolean isLengthMark;
        private final Map<String, Object> result = new HashMap<>();
        private final Stack<Map<String, Object>> tmp = new Stack<>();

        public Decoder(ByteBuf data) {
            this(data, false);
        }

        public Decoder(ByteBuf data, boolean isLengthMark) {
            this.source = data;
            this.isLengthMark = isLengthMark;
        }

        public Map<String, ?> execute() {
            this.tmp.clear();
            this.result.clear();
            while (this.source.isReadable()) {
                final InfoModel info = getInfo();
                if (info.type == 10) {
                    final Map<String, Object> custom = new HashMap<>();
                    if (this.tmp.isEmpty()) {
                        this.result.put(String.valueOf(info.tag), custom);
                    } else {
                        this.tmp.peek().put(String.valueOf(info.tag), custom);
                    }
                    this.tmp.add(custom);
                    continue;
                }
                if (info.type == 11) {
                    if (!this.tmp.isEmpty()) {
                        this.tmp.pop();
                    }
                    continue;
                }
                writeData(String.valueOf(info.tag), getData(info.type));
            }
            return this.result;
        }

        public void writeData(String key, Object value) {
            if (this.tmp.isEmpty()) {
                if (this.result.get(key) == null) {
                    this.result.put(key, value);
                } else {
                    if (this.result.get(key) instanceof List) {
                        ((List<Object>) this.result.get(key)).add(value);
                    } else {
                        final List<Object> t = new ArrayList<>();
                        t.add(this.result.get(key));
                        t.add(value);
                        this.result.put(key, t);
                    }
                }
            } else {
                final Object o = this.tmp.peek().get(key);
                if (o == null) {
                    this.tmp.peek().put(key, value);
                } else {
                    if (o instanceof List) {
                        ((List<Object>) o).add(value);
                    } else {
                        final List<Object> t = new ArrayList<>();
                        t.add(o);
                        t.add(value);
                        this.tmp.peek().put(key, t);
                    }
                }
            }
        }

        public InfoModel getInfo() {
            return getInfo(true);
        }

        public InfoModel getInfo(boolean isRead) {
            final int data = isRead
                    ? this.source.readByte()
                    : this.source.getByte(this.source.readerIndex());
            final int tag = data >> 4 & 0x0F;
            final int type = data & 0x0F;
            return new InfoModel(
                    tag == 0x0F ? (isRead
                            ? this.source.readByte()
                            : this.source.getByte(this.source.readerIndex() + 1)
                    ) : tag,
                    type
            );
        }

        public Object getData(int type) {
            final Object result;
            switch (type) {
                case 0:
                    result = this.getInt1();
                    break;
                case 1:
                    result = this.getInt2();
                    break;
                case 2:
                    result = this.getInt4();
                    break;
                case 3:
                    result = this.getInt8();
                    break;
                case 4:
                    result = this.getFloat();
                    break;
                case 5:
                    result = this.getDouble();
                    break;
                case 6:
                    result = this.getString1();
                    break;
                case 7:
                    result = this.getString4();
                    break;
                case 8:
                    result = this.getMap();
                    break;
                case 9:
                    result = this.getList();
                    break;
                case 12:
                    result = null;
                    break;
                case 13:
                    result = this.getSimpleKeyValue();
                    break;
                default:
                    throw new RuntimeException("[ " + type + " ] get data type error.");
            }
            return result;
        }

        public int getLength() {
            switch (getInt1()) {
                case 0:
                    return this.getInt1();
                case 1:
                    return this.getInt2();
                case 2:
                    return this.getInt4();
                case 12:
                    return -1;
                default:
                    throw new RuntimeException("length type error.");
            }
        }

        public int getInt1() {
            if (this.source.readableBytes() >= 1) {
                return this.source.readByte() & 0xFF;
            }
            return 0;
        }

        public int getInt2() {
            if (this.source.readableBytes() >= 2) {
                return ((this.source.readByte() & 0xFF) << 8)
                        | (this.source.readByte() & 0xFF);
            }
            return 0;
        }

        public int getInt4() {
            if (this.source.readableBytes() >= 4) {
                return this.source.readInt();
            }
            return 0;
        }

        public long getInt8() {
            if (this.source.readableBytes() >= 8) {
                return this.source.readLong();
            }
            return 0L;
        }

        public float getFloat() {
            if (this.source.readableBytes() >= 4) {
                return this.source.readFloat();
            }
            return 0L;
        }

        public double getDouble() {
            if (this.source.readableBytes() >= 8) {
                return this.source.readDouble();
            }
            return 0L;
        }

        public String getString1() {
            final byte[] bytes = new byte[getInt1()];
            if (this.source.readableBytes() >= bytes.length) {
                this.source.readBytes(bytes);
                return new String(bytes, StandardCharsets.UTF_8);
            }
            return null;
        }

        public String getString4() {
            final byte[] bytes = new byte[getInt4()];
            if (this.source.readableBytes() >= bytes.length) {
                this.source.readBytes(bytes);
                return new String(bytes, StandardCharsets.UTF_8);
            }
            return null;
        }

        public Map<Object, Object> getMap() {
            final int len = getLength();
            if (len < 0) {
                return null;
            }
            final Map<Object, Object> result = new HashMap<>();
            for (int i = 0; i < len; i++) {
                result.put(getMapKey(), getMapValue());
            }
            return result;
        }

        public Object getMapKey() {
            final InfoModel info = getInfo();
            if (info.tag == 0) {
                return getData(info.type);
            }
            throw new RuntimeException("map key error.");
        }

        public Object getMapValue() {
            final InfoModel info = getInfo();
            if (info.tag == 1) {
                return this.getData(info.type);
            }
            throw new RuntimeException("map value error.");
        }

        public List<Object> getList() {
            final int len = getLength();
            if (len < 0) {
                return null;
            }
            final List<Object> result = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                result.add(getListValue());
            }
            return result;
        }

        public Object getListValue() {
            if (this.source.isReadable()) {
                final InfoModel info = getInfo(false);
                if (info.tag == 0) {
                    if (info.type == 10 || info.type == 11) {
                        return new Decoder(this.source).execute();
                    } else {
                        getInfo(true);
                        return getData(info.type);
                    }
                }
                throw new Error("list value error.");
            } else {
                return null;
            }
        }

        public Object getSimpleKeyValue() {
            final int type = getInt1();
            if (type == 0) {
                int len = getLength();
                if (len == -1) {
                    this.source.readBytes(this.source.readableBytes()).release();
                    return null;
                }
                ByteBuf byteBuf = null;
                try {
                    byteBuf = this.source.readBytes(len);
                    if (this.isLengthMark) {
                        byteBuf.readInt();
                    }
                    if (byteBuf.readableBytes() >= 2) {
                        final int ss = byteBuf.getByte(byteBuf.readerIndex());
                        final int se = byteBuf.getByte(byteBuf.readableBytes() - 1);
                        if (ss == 123 && se == 125) {
                            final byte[] bytes = new byte[byteBuf.readableBytes()];
                            byteBuf.readBytes(bytes);
                            return new String(bytes);
                        }
                    }
                    return new Decoder(byteBuf).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if (byteBuf != null) {
                        byteBuf.release();
                    }
                }
            }
            throw new Error("simple key value error.");
        }
    }

    /**
     * 编码
     */
    @SuppressWarnings("ALL")
    public static class Encoder {
        private final Object source;
        private final boolean isLengthMark;
        private final boolean isSimpleKeyValue;
        private final List<Byte> result = new ArrayList<>();

        private boolean nextIsSimpleKeyValue;

        public Encoder(Object data) {
            this(data, false, true);
        }

        public Encoder(Object data, boolean isLengthMark, boolean isSimpleKeyValue) {
            this.source = data;
            this.isLengthMark = isLengthMark;
            this.isSimpleKeyValue = isSimpleKeyValue;
            this.nextIsSimpleKeyValue = isSimpleKeyValue;
        }

        public byte[] execute() {
            if (this.source instanceof Properties) {
                final Properties data = (Properties) this.source;
                final List<Integer> nums = new ArrayList<>();
                data.keySet().forEach(key -> {
                    if (key instanceof String) {
                        nums.add(Integer.parseInt((String) key));
                    }
                });
                nums.sort(Integer::compareTo);
                nums.forEach(k -> set(k, data.get(String.valueOf(k))));
            }
            final byte[] result = new byte[this.result.size()];
            for (int i = 0; i < this.result.size(); i++) {
                result[i] = this.result.get(i);
            }
            return result;
        }

        private void set(int index, Object data) {
            if (data == null || data == Void.TYPE) {
                setInfo(index, 12);
            } else {
                if (data instanceof Integer) {
                    if (((int) data) < 0xFF) {
                        setInfo(index, 0);
                        setInt1((int) data);
                    } else if (((int) data) < 0xFFFF) {
                        setInfo(index, 1);
                        setInt2((int) data);
                    } else {
                        setInfo(index, 2);
                        setInt4((int) data);
                    }
                } else if (data instanceof Long) {
                    setInfo(index, 3);
                    setInt8((long) data);
                } else if (data instanceof String) {
                    final int len = ((String) data).length();
                    if (len < 0xFF) {
                        this.setInfo(index, 6);
                        setInt1(len);
                    } else {
                        this.setInfo(index, 7);
                        setInt4(len);
                    }
                    setString((String) data);
                } else if (data instanceof Properties) {
                    setSimpleKeyValue(index, data);
                } else if (data instanceof Map) {
                    setMap(index, (Map<?, ?>) data);
                } else if (data instanceof List) {
                    setList(index, (List<?>) data);
                }
            }
        }

        private void setNull() {
            this.result.add((byte) 12);
        }

        private void setInfo(int index, int type) {
            if (index < 0x0F) {
                this.result.add((byte) (((index & 0x0F) << 4) | (type & 0x0F)));
            } else {
                this.result.add((byte) (type & 0x0F));
                this.result.add((byte) (index & 0xFF));
            }
        }

        private void setInt1(int data) {
            this.result.add((byte) (data & 0xFF));
        }

        private void setInt2(int data) {
            this.result.add((byte) ((data >> 8) & 0xFF));
            this.result.add((byte) (data & 0xFF));
        }

        private void setInt4(int data) {
            this.result.add((byte) ((data >> 24) & 0xFF));
            this.result.add((byte) ((data >> 16) & 0xFF));
            this.result.add((byte) ((data >> 8) & 0xFF));
            this.result.add((byte) (data & 0xFF));
        }

        private void setInt8(long data) {
            setInt4((int) (data >> 32));
            setInt4((int) (data & 0xFFFFFFFFL));
        }

        private void setString(String data) {
            setBytes(data.getBytes(StandardCharsets.UTF_8));
        }

        private void setBytes(byte[] data) {
            if (data != null) {
                for (final byte b : data) {
                    this.result.add(b);
                }
            }
        }

        private void setLength(int num) {
            if (num < 0xFF) {
                this.result.add((byte) 0);
                setInt1(num);
            } else if (num < 0xFFFF) {
                this.result.add((byte) 1);
                setInt2(num);
            } else {
                this.result.add((byte) 2);
                setInt4(num);
            }
        }

        private void setMap(int index, Map<?, ?> data) {
            setInfo(index, 8);
            if (data == null || data.isEmpty()) {
                setNull();
            } else {
                nextIsSimpleKeyValue = false;
                setLength(data.size());
                data.forEach((k, v) -> {
                    set(0, k);
                    set(1, v);
                });
            }
        }

        private void setList(int index, List<?> data) {
            setInfo(index, 9);
            if (data == null || data.isEmpty()) {
                setNull();
            } else {
                setLength(data.size());
                data.forEach(i -> set(0, i));
            }
        }

        private void setSimpleKeyValue(int index, Object data) {
            if (isSimpleKeyValue) {
                setInfo(index, 13);
                this.result.add((byte) 0);
                final byte[] bytes = new Encoder(data, false, nextIsSimpleKeyValue).execute();
                setLength(bytes.length + (isLengthMark ? 4 : 0));
                if (isLengthMark) {
                    setInt4(bytes.length + 4);
                }
                setBytes(bytes);
            } else {
                this.result.add((byte) 0x0a);
                setBytes(new Encoder(data, false, false).execute());
                this.result.add((byte) 0x0b);
            }
        }
    }
}
