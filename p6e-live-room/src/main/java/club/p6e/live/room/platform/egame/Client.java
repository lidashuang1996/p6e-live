//package club.p6e.live.room.platform.egame;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public class Client {
//
//    /** 编码器 */
//    private final Encoder encoder;
//    /** WebSocket 客户端对象 */
//    private final club.p6e.websocket.client.Client client;
//
//    /**
//     * 构造方法初始化
//     * @param client 客户端对象
//     * @param encoder 编码器对象
//     */
//    public Client(club.p6e.websocket.client.Client client, Encoder encoder) {
//        this.client = client;
//        this.encoder = encoder;
//    }
//
//    /**
//     * 发送登录的消息
//     * @param rid 房间的编号
//     */
//    public void sendLoginMessage(String rid) {
//       //  00000000: 0000 08b8 0012 0001 0001 0000 0000 0000  ................
//        //00000001: 0000 0700 0008 a143 6741 4245 6d45 6a50  .......CgABEmEjP
//        //00000002: 3477 4c47 676f 4348 5039 7062 6859 6b59  4wLGgoCHP9pbhYkY
//        //00000003: 7a64 6b4e 5464 6a4f 4745 744e 6a51 305a  zdkNTdjOGEtNjQ0Z
//        //00000004: 6930 305a 4456 684c 5749 314e 6d45 745a  i00ZDVhLWI1NmEtZ
//        //00000005: 4463 3259 6a6b 314e 4759 354e 574d 3249  Dc2Yjk1NGY5NWM2I
//        //00000006: 4145 3854 4677 4c47 6759 4f5a 5764 6862  AE8TFwLGgYOZWdhb
//        //00000007: 5756 6662 325a 6d61 574e 7059 5777 5142  WVfb2ZmaWNpYWwQB
//        //00000008: 4359 484f 5334 354c 6a6b 754f 5441 4354  CYHOS45LjkuOTACT
//        //00000009: 4641 4259 5241 4165 4141 4442 6764 755a  FABYRAAeAADBgduZ
//        //0000000a: 5852 3362 334a 7246 6741 4742 6e56 7a5a  XR3b3JrFgAGBnVzZ
//        //0000000b: 584a 7063 4259 4f4e 546b 754d 5463 794c  XJpcBYONTkuMTcyL
//        //0000000c: 6a45 304e 5334 784d 546b 4744 5842 6e5a  jE0NS4xMTkGDXBnZ
//        //0000000d: 3139 6a62 3231 7458 326c 755a 6d38 5841  19jb21tX2luZm8XA
//        //0000000e: 4141 4444 676f 4b41 687a 5f61 5734 5749  AADDgoKAhz_aW4WI
//        //0000000f: 4451 7a4e 4451 7752 6a67 774e 7a6b 7a52  DQzNDQwRjgwNzkzR
//        //00000010: 6b55 324d 305a 4652 6a59 7951 6b55 774e  kU2M0ZFRjYyQkUwN
//        //00000011: 4552 454f 5451 344d 554d 354a 6941 334e  EREOTQ4MUM5JiA3N
//        //00000012: 6a59 344d 6a64 424d 7a6c 444d 5467 7a4e  jY4MjdBMzlDMTgzN
//        //00000013: 7a68 4552 6a49 784d 7a4d 794d 6a68 444d  zhERjIxMzMyMjhDM
//        //00000014: 4546 464e 544d 784e 6a41 4251 4156 5743  EFFNTMxNjABQAVWC
//        //00000015: 5445 774d 5455 774d 7a6b 784f 5777 4c47  TEwMTUwMzkxOWwLG
//        //00000016: 674a 4e4c 7961 3145 4145 6d43 6c52 6b56  gJNLya1EAEmClRkV
//        //00000017: 6a68 5064 5756 7463 3063 3243 6a49 784d  jhPdWVtc0c2CjIxM
//        //00000018: 7a63 784e 5463 784d 5456 474a 454e 4a52  zcxNTcxMTVGJENJR
//        //00000019: 4638 354d 6a45 794f 4463 7a4d 446c 474d  F85MjEyODczMDlGM
//        //0000001a: 4463 304e 4441 7851 6a68 464d 6a52 4252  Dc0NDAxQjhFMjRBR
//        //0000001b: 6a5a 4752 6a59 7a51 7a63 334e 5173 4c47  jZGRjYzQzc3NQsLG
//        //0000001c: 6777 5742 7a6b 754f 5334 354c 6a6b 6742  gwWBzkuOS45LjkgB
//        //0000001d: 4467 4141 7759 4e51 5578 484c 575a 7359  DgAAwYNQUxHLWZsY
//        //0000001e: 5764 6664 486c 775a 5259 4142 6764 6663  WdfdHlwZRYABgdfc
//        //0000001f: 5756 6b61 6c39 3046 7741 4141 514a 4361  WVkal90FwAAAQJCa
//        //00000020: 476b 3265 5870 5065 4449 3151 5531 7263  Gk2eXpPeDI1QU1rc
//        //00000021: 6d5a 5961 4774 6153 4355 7951 6b56 4d61  mZYaGtaSCUyQkVMa
//        //00000022: 6c67 7954 4664 5456 6d4d 3561 4452 5a55  lgyTFdTVmM5aDRZU
//        //00000023: 5546 5457 5656 4f52 4773 7a54 5870 6e65  UFTWVVORGszTXpne
//        //00000024: 6b35 5557 5446 5965 6b55 7954 5770 724d  k5UWTFYekUyTWprM
//        //00000025: 6b39 5552 5868 4e65 6d4e 3557 564e 4e4a  k9URXhNemN5WVNNJ
//        //00000026: 544a 4759 5442 4a53 4355 7951 6d78 4c53  TJGYTBJSCUyQmxLS
//        //00000027: 6c5a 6f65 4668 5655 7a41 7855 5870 5354  lZoeFhVUzAxUXpST
//        //00000028: 6c4d 7752 6b4a 5352 4535 3455 3056 4757  lMwRkJSRE54U0VGW
//        //00000029: 4652 724d 585a 5361 305a 5156 4656 6f56  FRrMXZSa0ZQVFVoV
//        //0000002a: 5656 5753 6d39 615a 7a56 7357 6a4a 4764  VVWSm9aZzVsWjJGd
//        //0000002b: 4670 574f 585a 6162 5670 7757 544a 7361  FpWOXZabVpwWTJsa
//        //0000002c: 474a 4951 5556 6f61 5852 3357 6a4a 6b5a  GJIQUVoaXR3WjJkZ
//        //0000002d: 6d4a 4862 444a 6156 6a6c 3557 6c64 4761  mJHbDJaVjl5WldGa
//        //0000002e: 3167 7962 4731 5a4d 546c 305a 4559 3565  1gybG1ZMTl0ZEY5e
//        //0000002f: 6d52 7553 5856 6156 7a55 7759 3235 735a  mRuSXVaVzUwY25sZ
//        //00000030: 6d46 4556 6d5a 6952 3277 7957 6c59 3565  mFEVmZiR2wyWlY5e
//        //00000031: 5749 794f 5852 735a 324d 3154 4770 7264  WIyOXRsZ2M1TGprd
//        //00000032: 5539 544e 4456 775a 7a52 3454 5652 4664  U9TNDVwZzR4TVRFd
//        //00000033: 5531 3659 3356 4e61 6c45 7754 4770 4a65  U16Y3VNalEwTGpJe
//        //00000034: 4530 3352 5642 7654 566c 424d 5764 4563  E03RVBvTVlBMWdEc
//        //00000035: 7a68 424f 454d 4744 4546 4d52 7931 6d62  zhBOEMGDEFMRy1mb
//        //00000036: 4746 6e58 3342 7663 7859 4152 6742 5141  GFnX3BvcxYARgBQA
//        //00000037: 6d59 4f5a 5764 6862 5756 6662 325a 6d61  mYOZWdhbWVfb2Zma
//        //00000038: 574e 7059 5778 3241 4959 416c 6743 6d45  WNpYWx2AIYAlgCmE
//        //00000039: 6a59 344f 5449 314e 4455 774d 6a51 794d  jY4OTI1NDUwMjQyM
//        //0000003a: 5441 344d 6a4d 784d 4173 7144 4259 414a  TA4MjMxMAsqDBYAJ
//        //0000003b: 6743 6741 6249 3772 4a46 3377 6741 4133  gCgAbI7rJF3wgAA3
//        //0000003c: 6e37 5741 4f7a 3245 486c 4e62 3370 7062  n7WAOz2EHlNb3ppb
//        //0000003d: 4778 684c 7a55 754d 4341 6f54 5746 6a61  GxhLzUuMCAoTWFja
//        //0000003e: 5735 3062 334e 6f4f 7942 4a62 6e52 6c62  W50b3NoOyBJbnRlb
//        //0000003f: 4342 4e59 574d 6754 314d 6757 4341 784d  CBNYWMgT1MgWCAxM
//        //00000040: 4638 784e 5638 334b 5342 4263 4842 735a  F8xNV83KSBBcHBsZ
//        //00000041: 5664 6c59 6b74 7064 4338 314d 7a63 754d  VdlYktpdC81MzcuM
//        //00000042: 7a59 674b 4574 4956 4531 4d4c 4342 7361  zYgKEtIVE1MLCBsa
//        //00000043: 5774 6c49 4564 6c59 3274 764b 5342 4461  WtlIEdlY2tvKSBDa
//        //00000044: 484a 7662 5755 764f 5449 754d 4334 304e  HJvbWUvOTIuMC40N
//        //00000045: 5445 314c 6a45 314f 5342 5459 575a 6863  TE1LjE1OSBTYWZhc
//        //00000046: 6d6b 764e 544d 334c 6a4d 3243 7a6f 4741  mkvNTM3LjM2CzoGA
//        //00000047: 4174 4944 4667 4d62 4841 4269 6759 4146  AtIDFgMbHABigYAF
//        //00000048: 6741 6d4f 3364 304d 4639 5858 3246 5658  gAmO3d0MF9XX2FVX
//        //00000049: 305a 3364 5734 7757 6b39 6a63 454a 6d57  0Z3dW4wWk9jcEJmW
//        //0000004a: 6b5a 7862 5452 6d64 4452 5753 4531 4957  kZxbTRmdDRWSE1IW
//        //0000004b: 4552 614c 5331 5052 5763 3151 5763 344d  ERaLS1PRWc1QWc4M
//        //0000004c: 3370 494f 556c 3554 6c70 3456 576f 3064  3pIOUl5Tlp4VWo0d
//        //0000004d: 796f 714f 4141 4242 6842 335a 574a 6664  yoqOAABBhB3ZWJfd
//        //0000004e: 476c 6a61 3256 3058 334e 3059 5852 6c46  Glja2V0X3N0YXRlF
//        //0000004f: 6745 7743 3541 4243 796f 4b41 4145 5348  gEwC5ABCyoKAAESH
//        //00000050: 6156 346a 5359 554e 446b 334d 7a67 7a4e  aV4jSYUNDk3MzgzN
//        //00000051: 5459 3158 7a45 324d 6a6b 324f 5445 784d  TY1XzE2Mjk2OTExM
//        //00000052: 7a63 3541 4145 4141 6b6b 4d43 7849 6470  zc5AAEAAkkMCxIdp
//        //00000053: 5869 4e4a 6735 6c5a 3246 745a 5639 765a  XiNJg5lZ2FtZV9vZ
//        //00000054: 6d5a 7059 326c 6862 446b 4141 6759 4d5a  mZpY2lhbDkAAgYMZ
//        //00000055: 3278 7659 6d46 7358 3256 325a 5735 3042  2xvYmFsX2V2ZW50B
//        //00000056: 6852 7962 3239 7458 3256 325a 5735 3058  hRyb29tX2V2ZW50X
//        //00000057: 7a51 354e 7a4d 344d 7a55 324e 5173 4c4c  zQ5NzM4MzU2NQsLL
//        //00000058: 446f 4b42 6874 5252 3139 4952 5546 5356  DoKBhtRR19IRUFSV
//        //00000059: 454a 4651 5652 6655 4546 4852 5639 4d53  EJFQVRfUEFHRV9MS
//        //0000005a: 565a 4658 314a 5054 3030 5a44 4173 6143  VZFX1JPT00ZDAsaC
//        //0000005b: 4141 4342 674e 6861 5751 5743 5451 354e  AACBgNhaWQWCTQ5N
//        //0000005c: 7a4d 344d 7a55 324e 5159 4c59 6d56 6864  zM4MzU2NQYLYmVhd
//        //0000005d: 4639 795a 5842 7663 6e51 5841 4141 425f  F9yZXBvcnQXAAAB_
//        //0000005e: 4873 6963 3352 7958 3342 705a 4349 3649  Hsic3RyX3BpZCI6I
//        //0000005f: 6a51 354e 7a4d 344d 7a55 324e 5638 784e  jQ5NzM4MzU2NV8xN
//        //00000060: 6a49 354e 6a6b 784d 544d 3349 6977 6963  jI5NjkxMTM3Iiwic
//        //00000061: 324e 6c62 6d56 665a 6d78 685a 7949 364e  2NlbmVfZmxhZyI6N
//        //00000062: 4441 354e 6977 6963 3239 3163 6d4e 6c49  DA5Niwic291cmNlI
//        //00000063: 6a6f 784c 434a 7a64 484a 6661 5751 694f  joxLCJzdHJfaWQiO
//        //00000064: 6949 324f 446b 794e 5451 314d 4449 304d  iI2ODkyNTQ1MDI0M
//        //00000065: 6a45 774f 4449 7a4d 5441 694c 434a 795a  jEwODIzMTAiLCJyZ
//        //00000066: 5842 7663 6e52 6661 5735 6d62 7949 3665  XBvcnRfaW5mbyI6e
//        //00000067: 794a 7762 4746 305a 6d39 7962 5349 364e  yJwbGF0Zm9ybSI6N
//        //00000068: 4377 6963 324e 6c62 6d56 7a49 6a6f 304d  Cwic2NlbmVzIjo0M
//        //00000069: 446b 324c 434a 6861 5751 694f 6a51 354e  Dk2LCJhaWQiOjQ5N
//        //0000006a: 7a4d 344d 7a55 324e 5377 6959 5842 7761  zM4MzU2NSwiYXBwa
//        //0000006b: 5751 694f 694a 7362 3277 694c 434a 7761  WQiOiJsb2wiLCJwa
//        //0000006c: 5751 694f 6949 304f 5463 7a4f 444d 314e  WQiOiI0OTczODM1N
//        //0000006d: 6a56 664d 5459 794f 5459 354d 5445 7a4e  jVfMTYyOTY5MTEzN
//        //0000006e: 7949 7349 6e5a 705a 4349 3649 6949 7349  yIsInZpZCI6IiIsI
//        //0000006f: 6e4a 705a 4349 3649 6949 7349 6d78 705a  nJpZCI6IiIsImxpZ
//        //00000070: 4349 3649 6949 7349 6d31 6864 474e 6f58  CI6IiIsIm1hdGNoX
//        //00000071: 326c 6b49 6a6f 6949 6977 6964 476c 6b49  2lkIjoiIiwidGlkI
//        //00000072: 6a6f 6949 6977 6962 6d6c 6b49 6a6f 6949  joiIiwibmlkIjoiI
//        //00000073: 6977 6959 3246 305a 5764 7663 6e6c 6661  iwiY2F0ZWdvcnlfa
//        //00000074: 5751 694f 6949 694c 434a 6e61 575a 3058  WQiOiIiLCJnaWZ0X
//        //00000075: 326c 6b49 6a6f 6949 6977 6964 584a 7349  2lkIjoiIiwidXJsI
//        //00000076: 6a6f 6949 6977 6963 6d39 7662 5639 705a  joiIiwicm9vbV9pZ
//        //00000077: 4349 3649 6949 7349 6e42 685a 3256 6661  CI6IiIsInBhZ2Vfa
//        //00000078: 5751 694f 6949 794e 7a41 7849 6977 6962  WQiOiIyNzAxIiwib
//        //00000079: 4739 6e61 5735 6664 486c 775a 5349 3649  G9naW5fdHlwZSI6I
//        //0000007a: 6a45 694c 434a 3161 5734 694f 6a41 7349  jEiLCJ1aW4iOjAsI
//        //0000007b: 6d39 775a 5735 6661 5751 694f 6949 304f  m9wZW5faWQiOiI0O
//        //0000007c: 4459 314d 4441 334d 5467 694c 434a 7764  DY1MDA3MTgiLCJwd
//        //0000007d: 6d6c 6b49 6a6f 694e 6a67 354d 6a55 304e  mlkIjoiNjg5MjU0N
//        //0000007e: 5441 794e 4449 784d 4467 794d 7a45 7749  TAyNDIxMDgyMzEwI
//        //0000007f: 6977 6964 4756 7962 576c 7559 5778 6664  iwidGVybWluYWxfd
//        //00000080: 486c 775a 5349 364d 6977 6959 3267 694f  HlwZSI6MiwiY2giO
//        //00000081: 6949 694c 434a 7759 5764 6c58 334a 6c5a  iIiLCJwYWdlX3JlZ
//        //00000082: 6d56 795a 5849 694f 6949 694c 434a 6f59  mVyZXIiOiIiLCJoY
//        //00000083: 6d56 6864 4639 6c65 4851 694f 6e73 6961  mVhdF9leHQiOnsia
//        //00000084: 5735 6d62 7949 3665 794a 6b5a 575a 3162  W5mbyI6eyJkZWZ1b
//        //00000085: 6d4e 3049 6a6f 694d 4349 7349 6d5a 7359  mN0IjoiMCIsImZsY
//        //00000086: 5764 6664 486c 775a 5349 3649 6949 7349  WdfdHlwZSI6IiIsI
//        //00000087: 6d5a 7359 5764 6663 4739 7a49 6a6f 6949  mZsYWdfcG9zIjoiI
//        //00000088: 6e31 3966 5830 4c43 306b 4d2e 5f77 7574  n19fX0LC0kM. _wut
//        //00000089: 7979 7749 784a 7357 6a73 7043 4646 7a50  yywIxJsWjspCFFzP
//        //0000008a: 7465 3437 5344 4b71 354e 7455 6d53 656b  te47SDKq5NtUmSek
//        //0000008b: 4274 4e54 366d 383d                      BtNT6m8=
//
//
//        // barrage_ws_mode: true
//        //dc_keepalive_ws_mode: true
//        //token: "CgABEmEjP6wLGgoCHP9pbhYkZTU3MmU1YjctNWY4Ny00ZDVkLWI5ODgtYTM5YzM5MzIwNTcwIAE8TFwLGgYOZWdhbWVfb2ZmaWNpYWwQBCYHOS45LjkuOTACTFABYRAAeAADBgduZXR3b3JrFgAGBnVzZXJpcBYONTkuMTcyLjE0NS4xMTkGDXBnZ19jb21tX2luZm8XAAADDgoKAhz_aW4WIDQzNDQwRjgwNzkzRkU2M0ZFRjYyQkUwNEREOTQ4MUM5JiA3NjY4MjdBMzlDMTgzNzhERjIxMzMyMjhDMEFFNTMxNjABQAVWCTEwMTUwMzkxOWwLGgJNLya1EAEmClRWaTRScTFSQnQ2CjIwODMwMDk3MzZGJENJRF85MjEyODczMDlGMDc0NDAxQjhFMjRBRjZGRjYzQzc3NQsLGgwWBzkuOS45LjkgBDgAAwYMQUxHLWZsYWdfcG9zFgAGDUFMRy1mbGFnX3R5cGUWAAYHX3FlZGpfdBcAAAECQmhpNnl6T3gyNUFNa3JmWGhrWkglMkJFTGpYMkxXU1ZjOWg0WVFBU1lVTkRrM016Z3pOVFkxWHpFMk1qazJPVEV4TXpjeVlTTSUyRmEwSUglMkJsS0pWaHhYVVMwMVF6Uk5TMEZCUkROeFNFRlhUazF2UmtGUFRVaFVVVkpvWmc1bFoyRnRaVjl2Wm1acFkybGhiSEFFaGl0d1oyZGZiR2wyWlY5eVpXRmtYMmxtWTE5dGRGOXpkbkl1Wlc1MGNubGZhRFZmYkdsMlpWOXliMjl0bGdjNUxqa3VPUzQ1cGc0eE1URXVNemN1TWpRMExqSXhNN0VQb01ZQTFnRHM4QThDRgBQAmYOZWdhbWVfb2ZmaWNpYWx2AIYAlgCmEjY4OTI1NDUwMjQyMTA4MjMxMAsqDBYAJgCgAbI7rJF3wgAA3rzWAOz2EHlNb3ppbGxhLzUuMCAoTWFjaW50b3NoOyBJbnRlbCBNYWMgT1MgWCAxMF8xNV83KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvOTIuMC40NTE1LjE1OSBTYWZhcmkvNTM3LjM2CzoGAAtIDFgMbHABigYAFgAmO3d0MDhUUEhabG1hT2w3eGRfNy1ZWWx5WEQwSEh6dVprWXZsc3MwdXZBY2xMTlllNnRxMERkZVA3QSoqOAABBhB3ZWJfdGlja2V0X3N0YXRlFgEwC5ABCyoKAAESHaV4jSYUNDk3MzgzNTY1XzE2Mjk2OTExMzc5AAEAAkkMCxIdpXiNJg5lZ2FtZV9vZmZpY2lhbDkAAgYMZ2xvYmFsX2V2ZW50BhRyb29tX2V2ZW50XzQ5NzM4MzU2NQsLLDoKBhtRR19IRUFSVEJFQVRfUEFHRV9MSVZFX1JPT00ZDAsaCAACBgNhaWQWCTQ5NzM4MzU2NQYLYmVhdF9yZXBvcnQXAAAB_Hsic3RyX3BpZCI6IjQ5NzM4MzU2NV8xNjI5NjkxMTM3Iiwic2NlbmVfZmxhZyI6NDA5Niwic291cmNlIjoxLCJzdHJfaWQiOiI2ODkyNTQ1MDI0MjEwODIzMTAiLCJyZXBvcnRfaW5mbyI6eyJwbGF0Zm9ybSI6NCwic2NlbmVzIjo0MDk2LCJhaWQiOjQ5NzM4MzU2NSwiYXBwaWQiOiJsb2wiLCJwaWQiOiI0OTczODM1NjVfMTYyOTY5MTEzNyIsInZpZCI6IiIsInJpZCI6IiIsImxpZCI6IiIsIm1hdGNoX2lkIjoiIiwidGlkIjoiIiwibmlkIjoiIiwiY2F0ZWdvcnlfaWQiOiIiLCJnaWZ0X2lkIjoiIiwidXJsIjoiIiwicm9vbV9pZCI6IiIsInBhZ2VfaWQiOiIyNzAxIiwibG9naW5fdHlwZSI6IjEiLCJ1aW4iOjAsIm9wZW5faWQiOiI0ODY1MDA3MTgiLCJwdmlkIjoiNjg5MjU0NTAyNDIxMDgyMzEwIiwidGVybWluYWxfdHlwZSI6MiwiY2giOiIiLCJwYWdlX3JlZmVyZXIiOiIiLCJoYmVhdF9leHQiOnsiaW5mbyI6eyJkZWZ1bmN0IjoiMCIsImZsYWdfdHlwZSI6IiIsImZsYWdfcG9zIjoiIn19fX0LC0kM.Hp0AeMB7ZcKZ4-wjeeZ78HkOPL2CVsRaoafvTQRSYwo="
//        //
//
//        // https://m.egame.qq.com/live?anchorid
//        // https://share.egame.qq.com/cgi-bin/pgg_async_fcgi?app_info=%7B%22platform%22:4,%22terminal_type%22:2,%22egame_id%22:%22egame_official%22,%22imei%22:%22%22,%22version_code%22:%229.9.9.9%22,%22version_name%22:%229.9.9.9%22,%22ext_info%22:%7B%22_qedj_t%22:%22BhiIndJe2TaJBOI8ikm6vKJaqXCpDH4F%252BugQASYUNDk3MzgzNTY1XzE2Mjk2OTExMzcyYSNICkIvtzfiVhxsQjY1Q3h3TEFBRHV3QWFhTk1vRkFKQVNYQVJoZg5lZ2FtZV9vZmZpY2lhbHAEhitwZ2dfbGl2ZV9yZWFkX2lmY19tdF9zdnIuZW50cnlfaDVfbGl2ZV9yb29tlgc5LjkuOS45pg0xMTEuMTUuMTkzLjk1sQ%252BgxgDWAOzwDwI%253D%22,%22ALG-flag_type%22:%22%22,%22ALG-flag_pos%22:%22%22%7D,%22pvid%22:%22689254502421082310%22%7D&g_tk=&pgg_tk=1279588315&tt=1&captcha_token=%7B%22web_ticket%22:%22wt0BZoqX8fOrNMPceTnC3TNqLLKNUiZ1cdveCkGsOo11L5Qg23oNZaF2Q**%22%7D&_t=1629702219092
//
//
////        """
////        type: 0、3、9用户发言；7、33礼物信息；29、35欢迎信息；24、31系统提醒；23关注信息
////        """
//
////        client.sendMessageBinary(encoder.encode(Message.create(loginMessage)));
//    }
//
//    /**
//     * 发送加入组的消息
//     * @param rid 房间的编号
//     */
//    public void sendGroupMessage(String rid) {
//        final Map<String, String> groupMessage = new HashMap<>(2);
//        groupMessage.put("rid", rid);
//        groupMessage.put("gid", "-9999");
//        groupMessage.put("type", "joingroup");
//        client.sendMessageBinary(encoder.encode(Message.create(groupMessage)));
//    }
//
//    /**
//     * 发送接收全部礼物消息
//     */
//    public void sendAllGiftMessage() {
//        final Map<String, Object> allGiftMessage = new HashMap<>(2);
//        allGiftMessage.put("type", "dmfbdreq");
//        Map<String, Integer> dfl1 = new HashMap<>(2);
//        dfl1.put("ss", 0);
//        dfl1.put("sn", 105);
//        Map<String, Integer> dfl2 = new HashMap<>(2);
//        dfl2.put("ss", 0);
//        dfl2.put("sn", 106);
//        Map<String, Integer> dfl3 = new HashMap<>(2);
//        dfl3.put("ss", 0);
//        dfl3.put("sn", 107);
//        Map<String, Integer> dfl4 = new HashMap<>(2);
//        dfl4.put("ss", 0);
//        dfl4.put("sn", 108);
//        Map<String, Integer> dfl5 = new HashMap<>(2);
//        dfl5.put("ss", 0);
//        dfl5.put("sn", 110);
//        final List<Map<String, Integer>> dfl = new ArrayList<>(5);
//        dfl.add(dfl1);
//        dfl.add(dfl2);
//        dfl.add(dfl3);
//        dfl.add(dfl4);
//        dfl.add(dfl5);
//        allGiftMessage.put("dfl", dfl);
//        client.sendMessageBinary(encoder.encode(Message.create(allGiftMessage)));
//    }
//
//    /**
//     * 发送心跳消息
//     */
//    public void sendPantMessage() {
//        final Map<String, String> pantMessage = new HashMap<>(1);
//        pantMessage.put("type", "mrkl");
//        client.sendMessageBinary(encoder.encode(Message.create(pantMessage)));
//    }
//}
