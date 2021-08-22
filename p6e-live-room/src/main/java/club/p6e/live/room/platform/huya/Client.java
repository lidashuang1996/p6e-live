package club.p6e.live.room.platform.huya;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    private final String rid;
    /** 编码器 */
    private final Encoder encoder;
    /** 客户端 */
    private final club.p6e.websocket.client.Client client;

    /**
     * 构造器初始化
     * @param client 客户端
     * @param encoder 编码器
     */
    public Client(club.p6e.websocket.client.Client client, Encoder encoder, String rid) {
        this.rid = rid;
        this.client = client;
        this.encoder = encoder;
    }

    public void monitorEvent() {
        // 6501 礼物
        // 1400 弹幕
        final Template<BarrageEventTemplate> template = new Template<>();
        template.setType(16);
        template.setData(new BarrageEventTemplate(rid, rid));
        this.client.sendMessageBinary(encoder.encode(Message.create(template.toList())));
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

//00000000: 0003 1d00 0102 fc00 0002 fc10 032c 3c40  .............,<@
//00000001: 0156 086f 6e6c 696e 6575 6966 0f4f 6e55  .V.onlineuif.OnU
//00000002: 7365 7248 6561 7274 4265 6174 7d00 0102  serHeartBeat}...
//00000003: cd08 0001 0604 7452 6571 1d00 0102 bf0a  ......tReq......
//00000004: 0a0c 1620 3061 6462 3134 3832 6565 3934  ... 0adb1482ee94
//00000005: 3764 3630 6535 3031 3061 3162 3062 3162  7d60e5010a1b0b1b
//00000006: 6537 6639 2600 361a 7765 6268 3526 3231  e7f9&.6.webh5&21
//00000007: 3038 3138 3136 3235 2677 6562 736f 636b  08181625&websock
//00000008: 6574 4700 0002 5576 706c 6179 6572 5f73  etG...Uvplayer_s
//00000009: 6261 6e6e 6572 5f31 3334 3636 3039 3731  banner_134660971
//0000000a: 355f 3133 3436 3630 3937 3135 3d31 3b20  5_1346609715=1;
//0000000b: 5f5f 7961 6d69 645f 7474 313d 302e 3737  __yamid_tt1=0.77
//0000000c: 3730 3034 3231 3036 3536 3738 3736 3b20  70042106567876;
//0000000d: 5f5f 7961 6d69 645f 6e65 773d 4339 3530  __yamid_new=C950
//0000000e: 4236 4345 3831 3430 3030 3031 3632 4246  B6CE8140000162BF
//0000000f: 3144 3430 4546 3630 4333 3330 3b20 6761  1D40EF60C330; ga
//00000010: 6d65 5f64 6964 3d54 4b6a 4565 7a48 335a  me_did=TKjEezH3Z
//00000011: 5878 7847 4e37 6262 6e4e 7561 4a4f 5943  XxxGN7bbnNuaJOYC
//00000012: 4d33 6f55 7039 3346 3655 3b20 7564 625f  M3oUp93F6U; udb_
//00000013: 6775 6964 6461 7461 3d32 6531 3235 6438  guiddata=2e125d8
//00000014: 6264 3139 6134 6563 3438 3034 6334 6266  bd19a4ec4804c4bf
//00000015: 3536 3165 6363 3638 373b 2061 6c70 6861  561ecc687; alpha
//00000016: 5661 6c75 653d 302e 3830 3b20 6775 6964  Value=0.80; guid
//00000017: 3d30 6164 6231 3438 3265 6539 3437 6436  =0adb1482ee947d6
//00000018: 3065 3530 3130 6131 6230 6231 6265 3766  0e5010a1b0b1be7f
//00000019: 393b 2075 6462 5f70 6173 7364 6174 613d  9; udb_passdata=
//0000001a: 333b 205f 5f79 6173 6d69 643d 302e 3737  3; __yasmid=0.77
//0000001b: 3730 3034 3231 3036 3536 3738 3736 3b20  70042106567876;
//0000001c: 5f79 6173 6964 733d 5f5f 726f 6f74 7369  _yasids=__rootsi
//0000001d: 6425 3344 4339 3744 3438 4430 4532 3630  d%3DC97D48D0E260
//0000001e: 3030 3031 3338 3145 4338 3030 3131 4545  0001381EC80011EE
//0000001f: 3132 3843 3b20 536f 756e 6456 616c 7565  128C; SoundValue
//00000020: 3d30 2e30 303b 2072 6570 5f63 6e74 3d34  =0.00; rep_cnt=4
//00000021: 343b 2048 6d5f 6c76 745f 3531 3730 3062  4; Hm_lvt_51700b
//00000022: 3663 3732 3266 3562 6234 6366 3339 3930  6c722f5bb4cf3990
//00000023: 3661 3539 3665 6134 3166 3d31 3632 3934  6a596ea41f=16294
//00000024: 3237 3634 352c 3136 3239 3630 3230 3633  27645,1629602063
//00000025: 2c31 3632 3936 3037 3435 322c 3136 3239  ,1629607452,1629
//00000026: 3631 3932 3132 3b20 6973 496e 4c69 7665  619212; isInLive
//00000027: 526f 6f6d 3d74 7275 653b 2048 6d5f 6c70  Room=true; Hm_lp
//00000028: 7674 5f35 3137 3030 6236 6337 3232 6635  vt_51700b6c722f5
//00000029: 6262 3463 6633 3939 3036 6135 3936 6561  bb4cf39906a596ea
//0000002a: 3431 663d 3136 3239 3632 3732 3431 3b20  41f=1629627241;
//0000002b: 6875 7961 5f66 6c61 7368 5f72 6570 5f63  huya_flash_rep_c
//0000002c: 6e74 3d35 3636 3b20 6875 7961 5f77 6562  nt=566; huya_web
//0000002d: 5f72 6570 5f63 6e74 3d39 3432 5c66 0643  _rep_cnt=942\f.C
//0000002e: 6872 6f6d 650b 1250 43a2 3322 5043 a233  hrome..PC.3"PC.3
//0000002f: 4250 43a2 3350 0160 077c 8c9c ac0b 8c98  BPC.3P.`.|......
//00000030: 0ca8 0c2c 3625 6234 3462 3763 3736 6637  ...,6%b44b7c76f7
//00000031: 3663 3336 6663 3a62 3434 6237 6337 3666  6c36fc:b44b7c76f
//00000032: 3736 6333 3666 633a 303a 304c 5c66 00    76c36fc:0:0L\f.


// 礼物列表
// 00000000: 0003 1d00 0102 f500 0002 f510 032c 3c40  .............,<@
//00000001: ff56 0d50 726f 7073 5549 5365 7276 6572  .V.PropsUIServer
//00000002: 660c 6765 7450 726f 7073 4c69 7374 7d00  f.getPropsList}.
//00000003: 0102 c408 0001 0604 7452 6571 1d00 0102  ........tReq....
//00000004: b60a 1a0c 1620 3061 6462 3134 3832 6565  ..... 0adb1482ee
//00000005: 3934 3764 3630 6535 3031 3061 3162 3062  947d60e5010a1b0b
//00000006: 3162 6537 6639 2600 361a 7765 6268 3526  1be7f9&.6.webh5&
//00000007: 3231 3038 3138 3136 3235 2677 6562 736f  2108181625&webso
//00000008: 636b 6574 4700 0002 4a5f 5f79 616d 6964  cketG...J__yamid
//00000009: 5f74 7431 3d30 2e37 3737 3030 3432 3130  _tt1=0.777004210
//0000000a: 3635 3637 3837 363b 205f 5f79 616d 6964  6567876; __yamid
//0000000b: 5f6e 6577 3d43 3935 3042 3643 4538 3134  _new=C950B6CE814
//0000000c: 3030 3030 3136 3242 4631 4434 3045 4636  0000162BF1D40EF6
//0000000d: 3043 3333 303b 2067 616d 655f 6469 643d  0C330; game_did=
//0000000e: 544b 6a45 657a 4833 5a58 7878 474e 3762  TKjEezH3ZXxxGN7b
//0000000f: 626e 4e75 614a 4f59 434d 336f 5570 3933  bnNuaJOYCM3oUp93
//00000010: 4636 553b 2075 6462 5f67 7569 6464 6174  F6U; udb_guiddat
//00000011: 613d 3265 3132 3564 3862 6431 3961 3465  a=2e125d8bd19a4e
//00000012: 6334 3830 3463 3462 6635 3631 6563 6336  c4804c4bf561ecc6
//00000013: 3837 3b20 616c 7068 6156 616c 7565 3d30  87; alphaValue=0
//00000014: 2e38 303b 2067 7569 643d 3061 6462 3134  .80; guid=0adb14
//00000015: 3832 6565 3934 3764 3630 6535 3031 3061  82ee947d60e5010a
//00000016: 3162 3062 3162 6537 6639 3b20 7564 625f  1b0b1be7f9; udb_
//00000017: 7061 7373 6461 7461 3d33 3b20 5f5f 7961  passdata=3; __ya
//00000018: 736d 6964 3d30 2e37 3737 3030 3432 3130  smid=0.777004210
//00000019: 3635 3637 3837 363b 205f 7961 7369 6473  6567876; _yasids
//0000001a: 3d5f 5f72 6f6f 7473 6964 2533 4443 3937  =__rootsid%3DC97
//0000001b: 4434 3844 3045 3236 3030 3030 3133 3831  D48D0E2600001381
//0000001c: 4543 3830 3031 3145 4531 3238 433b 2053  EC80011EE128C; S
//0000001d: 6f75 6e64 5661 6c75 653d 302e 3030 3b20  oundValue=0.00;
//0000001e: 7265 705f 636e 743d 3434 3b20 486d 5f6c  rep_cnt=44; Hm_l
//0000001f: 7674 5f35 3137 3030 6236 6337 3232 6635  vt_51700b6c722f5
//00000020: 6262 3463 6633 3939 3036 6135 3936 6561  bb4cf39906a596ea
//00000021: 3431 663d 3136 3239 3432 3736 3435 2c31  41f=1629427645,1
//00000022: 3632 3936 3032 3036 332c 3136 3239 3630  629602063,162960
//00000023: 3734 3532 2c31 3632 3936 3139 3231 323b  7452,1629619212;
//00000024: 2068 6969 646f 5f75 693d 302e 3034 3737   hiido_ui=0.0477
//00000025: 3733 3239 3537 3530 3636 3938 3b20 6973  732957506698; is
//00000026: 496e 4c69 7665 526f 6f6d 3d74 7275 653b  InLiveRoom=true;
//00000027: 2048 6d5f 6c70 7674 5f35 3137 3030 6236   Hm_lpvt_51700b6
//00000028: 6337 3232 6635 6262 3463 6633 3939 3036  c722f5bb4cf39906
//00000029: 6135 3936 6561 3431 663d 3136 3239 3633  a596ea41f=162963
//0000002a: 3235 3835 3b20 6875 7961 5f66 6c61 7368  2585; huya_flash
//0000002b: 5f72 6570 5f63 6e74 3d36 3339 3b20 6875  _rep_cnt=639; hu
//0000002c: 7961 5f77 6562 5f72 6570 5f63 6e74 3d31  ya_web_rep_cnt=1
//0000002d: 3131 325c 6606 4368 726f 6d65 0b26 0030  112\f.Chrome.&.0
//0000002e: 2146 0051 2749 6264 d3a5 d072 64d3 a5d0  !F.Q'Ibd...rd...
//0000002f: 8264 d3a5 d09c 0b8c 980c a80c 2c36 2534  .d..........,6%4
//00000030: 3131 3334 6565 3939 3661 3562 6337 393a  1134ee996a5bc79:
//00000031: 3431 3133 3465 6539 3936 6135 6263 3739  41134ee996a5bc79
//00000032: 3a30 3a30 4c5c 6600                      :0:0L\f.
}
