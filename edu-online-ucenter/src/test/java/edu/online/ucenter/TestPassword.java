package edu.online.ucenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname edu.online.ucenter.TestPassword
 * @Description TODO
 * @Date 2020/3/4 18:42
 * @Created by zhoutao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPassword {
    @Test
    public void test01() {
        //原始密码
        String password = "111111";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //使用BCrypt加密，每次加密使用一个随机盐
        for (int i = 0; i < 10; i++) {
            String encode = bCryptPasswordEncoder.encode(password);
            System.out.println(encode);
            //校验
            boolean matches = bCryptPasswordEncoder.matches(password, encode);
            System.out.println(matches);
        }
    }
}
