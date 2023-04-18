//package club.p6e.live.room.platform.douyin;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public class F {
//
//    @SuppressWarnings("all")
//    private static Map<Integer, Object> decodeByteBufToMessage(ByteBuf byteBuf) {
//        final Map<Integer, Object> result = new HashMap<>(16);
//        while (true) {
//            final int[] tt = getTagAndType(byteBuf);
//            if (tt == null) {
//                break;
//            }
//            switch (tt[1]) {
//                case 0:
//                    Object o1 = getType0(byteBuf);
//                    result.put(tt[0], o1);
//                    break;
//                case 1:
//                    Object o2 = getType1(byteBuf);
//                    result.put(tt[0], o2);
//                    break;
//                case 2:
//                    final ByteBuf buf = getType2(byteBuf);
//                    try {
//                        if (buf == null) {
//                            mapWriteData(result, tt[0], "");
//                        } else if (buf.readableBytes() == 1) {
//                            mapWriteData(result, tt[0], Utils.bytesToHex(buf.array()));
//                        } else {
//                            final int[] tt2 = getTagAndType(buf);
//                            buf.resetReaderIndex();
//                            if (tt2 == null) {
//                                mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
//                            } else {
//                                if ((tt2[0] == 1 && (tt2[1] == 0 || tt2[1] == 1 || tt2[1] == 2))
//                                        || (tt2[0] == 4 && tt2[1] == 2)
//                                        || ((tt[0] == 2 || tt[0] == 21) && tt2[0] == 2 && tt2[1] == 2)) {
//                                    mapWriteData(result, tt[0], decodeByteBufToMessage(buf));
//                                } else {
//                                    final int mark = buf.readByte();
//                                    buf.resetReaderIndex();
//                                    if (MARK == mark) {
//                                        final Object o = tsFun(buf);
//                                        if (o == null) {
//                                            mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
//                                        } else {
//                                            mapWriteData(result, tt[0], o);
//                                        }
//                                    } else {
//                                        mapWriteData(result, tt[0], buf.toString(StandardCharsets.UTF_8));
//                                    }
//                                }
//                            }
//                        }
//                    } finally {
//                        if (buf != null) {
//                            buf.release();
//                        }
//                    }
//                    break;
//                default:
//                    final byte[] readBytes = new byte[byteBuf.readerIndex()];
//                    byteBuf.readBytes(readBytes);
//                    throw new RuntimeException("decodeByteBufToMessages type is null." + Utils.bytesToHex(readBytes) + "  \n\n " + result);
//            }
//        }
//        return result;
//    }
//
//
//
//}
