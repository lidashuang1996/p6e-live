//package club.p6e.live.room.platform.egame;
//
//import club.p6e.live.room.utils.Utils;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public class Test {
//
//    public static void main(String[] args) {
//        String a = "00000000: 0000 0100 0012 0001 0003 0000 0000 0000  ................\n" +
//                "00000001: 0000 0000 00ea 0646 5147 5f48 4541 5254  .......FQG_HEART\n" +
//                "00000002: 4245 4154 5f45 5645 4e54 5f4e 4f42 4c45  BEAT_EVENT_NOBLE\n" +
//                "00000003: 5f4e 554d 5f4e 4f54 4946 595f 3439 3733  _NUM_NOTIFY_4973\n" +
//                "00000004: 3833 3536 355f 3136 3239 3639 3131 3337  83565_1629691137\n" +
//                "00000005: 5f31 3632 3936 3938 3438 3739 3631 1002  _1629698487961..\n" +
//                "00000006: 2900 0106 8406 4651 475f 4845 4152 5442  ).....FQG_HEARTB\n" +
//                "00000007: 4541 545f 4556 454e 545f 4e4f 424c 455f  EAT_EVENT_NOBLE_\n" +
//                "00000008: 4e55 4d5f 4e4f 5449 4659 5f34 3937 3338  NUM_NOTIFY_49738\n" +
//                "00000009: 3335 3635 5f31 3632 3936 3931 3133 375f  3565_1629691137_\n" +
//                "0000000a: 3136 3239 3639 3834 3837 3936 3116 2351  1629698487961.#Q\n" +
//                "0000000b: 475f 4845 4152 5442 4541 545f 4556 454e  G_HEARTBEAT_EVEN\n" +
//                "0000000c: 545f 4e4f 424c 455f 4e55 4d5f 4e4f 5449  T_NOBLE_NUM_NOTI\n" +
//                "0000000d: 4659 260c 7b22 6e75 6d22 3a31 3831 327d  FY&.{\"num\":1812}\n" +
//                "0000000e: 380c 4d00 0003 0107 1438 0c42 6123 39b7  8.M......8.Ba#9.\n" +
//                "0000000f: 5261 2339 cb69 0c73 0000 001d 8fd8 fce3  Ra#9.i.s........\n";
//
//        StringBuilder sb = new StringBuilder();
//        String[] as = a.split("\n");
//        for (String s : as) {
//            sb.append(s.substring(10,49).replaceAll(" ", ""));
//        }
//        ByteBuf byteBuf = Unpooled.buffer();
//        byteBuf.writeBytes(Utils.hexToByte(sb.substring(44)));
//        System.out.println(
//                        new Taf.Decode().execute(byteBuf.resetReaderIndex())
//        );
//    }
//
//}
