package club.p6e.live.room.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 常用工具类
 * 1. UUID 生成
 * 2. 大端模式 int 转 byte
 * 3. 小端模式 int 转 byte
 * 4. 大端模式 byte 转 int
 * 5. 小端模式 byte 转 int
 * 6. hex 转 byte 数组
 * 7. byte 数组转 hex
 * 9. Zlib 压缩
 * 10. Zlib 解压
 * 11. 消息解析
 * 12. byte 数组和并
 * 13. byte 数组获取指定长度的内容
 * 14. Object 序列化 JSON 工具
 * 15. JSON 反序列工具
 * @version 1.0
 */
public final class Utils {

    /**
     * 生成 UUID
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String generateUuid(int len) {
        final StringBuilder sb = new StringBuilder(generateUuid());
        for (int i = sb.length(); i < len; i++) {
            sb.append("0");
        }
        return sb.toString().substring(0, len);
    }


    /**
     * 以大端模式将 int 转成 byte[]
     */
    public static byte[] intToBytesBig(int value) {
        return new byte [] {
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    /**
     * 以小端模式将 int 转成 byte[]
     */
    public static byte[] intToBytesLittle(int value) {
        return new byte [] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    /**
     * 以大端模式将 byte[]转成 int
     */
    public static int bytesToIntBig(byte[] src) {
        return (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
    }

    /**
     * 以小端模式将 byte[] 转成 int
     */
    public static int bytesToIntLittle(byte[] bytes) {
        return ((bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF) << 8)
                | ((bytes[2] & 0xFF) << 16)
                | ((bytes[3] & 0xFF) << 24));
    }

    /**
     *
     */
    public static int bytesToHigh8Low8(byte[] bytes) {
        return (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
    }

    public static byte[] integerToHigh8Low8(int data) {
        return new byte[] { (byte) ((data >> 8) & 0xFF), (byte) (data & 0xFF) };
    }

    public static String integerToBinaryString(int data) {
        return Integer.toBinaryString(data);
    }

    public static String integerToBinaryString(int data, int len) {
        final String r = "0000000000000000" + Integer.toBinaryString(data);
        return r.substring(r.length() - len);
    }

    public static byte binaryToByte(String data) {
        return (byte) Integer.parseInt(data, 2);
    }

    public static String byteTo8421Code(byte b) {
        return String.valueOf(b >> 4 & 0x0F) + (b & 0x0F);
    }

    public static String bytesTo8421Code(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byteTo8421Code(b));
        }
        return sb.toString();
    }

    public static byte[] codeTo8421Bytes(String content) {
        int z = 0;
        final byte[] bytes = new byte[content.length() / 2];
        for (int i = 0; i < content.length(); i++) {
            int item = Integer.parseInt(String.valueOf(content.charAt(i))) & 0x0F;
            if (i % 2 != 0) {
                bytes[i / 2] = (byte) (z | item);
            } else {
                z = item << 4;
            }
        }
        return bytes;
    }

    public static byte[] bytesArrayIntercept(byte[] bytes, int index, int len) {
        final byte[] result = new byte[len];
        System.arraycopy(bytes, index, result, 0, len);
        return result;
    }

    /**
     * hex转byte数组
     */
    public static byte[] hexToByte(String hex){
        int m, n;
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = (byte) intVal;
        }
        return ret;
    }

    /**
     * byte数组转hex
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Zlib 压缩
     */
    public static byte[] compressZlib(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!deflater.finished()) {
                int i = deflater.deflate(buf);
                byteArrayOutputStream.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        deflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Zlib 解压
     */
    public static byte[] decompressZlib(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!inflater.finished()) {
                int i = inflater.inflate(buf);
                byteArrayOutputStream.write(buf,0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        inflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 消息解析
     */
    public static String translate(String content, String... contents) {
        if (contents == null || contents.length == 0 || contents.length % 2 != 0) return content;
        else {
            for (int i = 0; i < contents.length; i++) {
                if (i % 2 == 0) {
                    content = content.replaceAll("\\$\\{" + contents[i] + "}", contents[i + 1]);
                }
            }
            return content;
        }
    }


    public static byte[] byteListToByteArray(List<Byte> list) {
        final byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }


    public static boolean isBaseType(Object object) {
        final Class<?> className = object.getClass();
        return className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class) ||
                className.equals(String.class);
    }

}
