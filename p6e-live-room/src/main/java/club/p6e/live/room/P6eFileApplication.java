package club.p6e.live.room;

import club.p6e.live.room.platform.huya.Application;
import club.p6e.live.room.platform.huya.Client;
import club.p6e.live.room.platform.huya.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class P6eFileApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eFileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(P6eFileApplication.class, args);
        P6eLiveRoomApplication.init();

        System.out.println(
                Application.getLiveChannelId("https://www.huya.com/baozha")
        );

        // https://www.huya.com/641641
        new Application("wss://3b3fe620-ws.va.huya.com/", "1199515480194", new LiveRoomCallback.HuYa() {
            @Override
            public void onOpen(Client client) {

            }

            @Override
            public void onClose(Client client) {

            }

            @Override
            public void onError(Client client, Throwable throwable) {

            }

            @Override
            public void onMessage(Client client, List<Message> messages) {
                for (Message message : messages) {
                    LOGGER.info(message.data().toString());
                }
            }

        }).connect();
    }


    // 00000000: abcd 0001 0000 0129 0000 0080 0800 10da  .......)........
    //00000001: bea8 eaea afe3 0118 0038 6d40 014a 8802  .........8m@.J..
    //00000002: 0801 1283 0243 6852 6859 325a 3162 6935  .....ChRhY2Z1bi5
    //00000003: 6863 476b 7564 6d6c 7a61 5852 7663 6935  hcGkudmlzaXRvci5
    //00000004: 7a64 424a 7766 4758 6947 4e73 4f61 3530  zdBJwfGXiGNsOa50
    //00000005: 364c 5a64 516b 7a74 6865 5575 7762 6969  6LZdQkztheUuwbii
    //00000006: 4a78 3269 5561 3757 5445 7949 6654 5741  Jx2iUa7WTEyIfTWA
    //00000007: 5869 6a51 7549 6e61 5a59 6b6e 3148 524f  XijQuInaZYkn1HRO
    //00000008: 5645 3338 6d32 664e 6148 4f43 6b52 5f38  VE38m2fNaHOCkR_8
    //00000009: 4c39 4132 7a64 6b48 5a37 546c 3931 4845  L9A2zdkHZ7Tl91HE
    //0000000a: 3776 4956 6431 6e51 5937 6c30 6f4a 5744  7vIVd1nQY7l0oJWD
    //0000000b: 3951 4352 644b 4e41 4348 4a55 6977 6659  9QCRdKNACHJUiwfY
    //0000000c: 6551 5648 6148 7a2d 5649 775a 564c 5368  eQVHaHz-VIwZVLSh
    //0000000d: 365f 3049 4e66 6338 7452 426f 5353 6d2d  6_0INfc8tRBoSSm-
    //0000000e: 364b 6941 5536 5569 4551 6963 387a 7259  6KiAU6UiEQic8zrY
    //0000000f: 5735 5273 6e49 6942 7967 794f 6c73 5774  W5RsnIiBygyOlsWt
    //00000010: 474b 3448 7548 7448 6f36 6e4f 6765 5f67  GK4HuHtHo6nOge_g
    //00000011: 556f 7833 696e 5331 4750 5669 5a31 2d33  Uox3inS1GPViZ1-3
    //00000012: 4e46 6967 464d 4145 5001 6209 4143 4655  NFigFMAEP.b.ACFU
    //00000013: 4e5f 4150 50b9 9290 7909 320f 5001 d0ac  N_APP...y.2.P...
    //00000014: c702 615b 1ca7 f58a e8f6 09eb 1325 2587  ..a[.........%%.
    //00000015: d886 10f7 4504 2b5a 6be3 c6bc c0f4 5294  ....E.+Zk.....R.
    //00000016: f099 a90a f3ad 29de 3945 bfd5 5777 290c  ......).9E..Ww).
    //00000017: 8b92 5d96 5fe3 1f40 ee93 e7c1 e9b8 a32f  ..]._..@......./
    //00000018: 18d3 d68d ec21 560a 551c e120 2ed8 5c5e  .....!V.U.. ..\^
    //00000019: 8861 a264 9456 f9a1 a2e4 7c1b 1e5b d4ba  .a.d.V....|..[..
    //0000001a: 19e2 5335 8454 8265 b0ae b2b8 182d 9ebe  ..S5.T.e.....-..
    //0000001b: d125 ffe5 c9                             .%...

    // 00000000: abcd 0001 0000 0026     0000 0040      080d 10da  .......&...@....
    //00000001: bea8 eaea afe3 0118
    // f19d 9aa0 bbed 80a7  ................
    //00000002: 3838   2640 0250 0262 0941 4346 554e 5f41  88&@.P.b.ACFUN_A
    //00000003: 5050 40d7 8e3b 1b46 64df a52e 1845 bd3c  PP@..;.Fd....E.<

    //00000004: 2233 ac63 2b67 aac3 0be0 aac3 af7a 1d6d  "3.c+g.......z.m
    //00000005: 2cd9 3c69 aa57 9c77 2c38 be3a 1cbd 471f  ,.<i.W.w,8.:..G.
    //00000006: daf7 4de4 d2fb aac9 9fc0 b5e6 8a6c eba7  ..M..........l..
    //00000007: d0ec                                     ..
}
