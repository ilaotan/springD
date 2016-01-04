package com.springD.user;

import com.springD.framework.encoder.Md5PwdEncoder;

/**
 * Created by tan on 2016/1/4.
 */
public class Generate {

    public static void main(String[] args) {

        String username = "tanlsh";
        String salt1 = "r4QD4vG9";

        String passwd1 = Md5PwdEncoder.encodePassword(salt1+username,"");

        String passwd2 = Md5PwdEncoder.encodePassword(passwd1,"");

        String radomSalt = "b123";

        String finalPwd = Md5PwdEncoder.encodePassword(radomSalt+passwd2,"");

        System.out.print(finalPwd);

    }
}
