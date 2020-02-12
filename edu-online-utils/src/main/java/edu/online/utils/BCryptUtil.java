package edu.online.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Classname BCryptUtil
 * @Description 密码加密工具
 * @Date 2019/12/7 17:59
 * @Created by zhoutao
 */
public class BCryptUtil {

    public static String encode(String password){
     PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
        String pwdEncode = passwordEncoder.encode(password);
        return  pwdEncode;
    }

    public static boolean matches(String password,String pwdEncode){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(password, pwdEncode);
        return f;
    }
}
