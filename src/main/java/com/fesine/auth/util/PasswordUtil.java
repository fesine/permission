package com.fesine.auth.util;

import java.util.Random;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public class PasswordUtil {

    public final static String[] WORD={
            "a","b","c","d","e","f","g","h",
            "j","k","m","n","p","q","r","s",
            "t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H",
            "J","K","M","N","P","Q","R","S",
            "T","U","V","W","X","Y","Z"
    };

    public final static String [] NUM={
      "2","3","4","5","6","7","8","9"
    };

    public static String randomPassword(){
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        int length = random.nextInt(3)+8;

        for (int i = 0; i < length; i++) {
            if (flag) {
                sb.append(NUM[random.nextInt(NUM.length)]);
            } else {
                sb.append(WORD[random.nextInt(WORD.length)]);
            }
            flag = !flag;
        }
        return sb.toString();
    }

}
