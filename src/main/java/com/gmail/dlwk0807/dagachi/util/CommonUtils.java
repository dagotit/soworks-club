package com.gmail.dlwk0807.dagachi.util;

import java.util.Random;

public class CommonUtils {

    // 인증번호 8자리 무작위 생성
    public static String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0; i<8; i++) {
            // 0~2 사이의 값을 랜덤하게 받아와 idx에 집어넣습니다.
            int idx = random.nextInt(3);

            // 랜덤하게 idx를 받았으면, 그 값을 switchcase를 통해 또 꼬아버립니다.
            // 숫자와 ASCII 코드를 이용합니다.
            switch (idx) {
                case 0 :
                    // a(97) ~ z(122)
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    // A(65) ~ Z(90)
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    // 0 ~ 9
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return key.toString();
    }
}
