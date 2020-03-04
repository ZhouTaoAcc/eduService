package edu.online.oauth.test;

import edu.online.Entity.ucenter.VO.UserVO;
import edu.online.oauth.feign.UserFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname TestFeignClient
 * @Description TODO
 * @Date 2020/3/4 19:03
 * @Created by zhoutao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFeignClient {
    @Autowired
    UserFeignClient userFeignClient;
    @Test
    public void testFeign(){
        String username="itcast";
        UserVO userVO = userFeignClient.getUser(username);
        System.out.println(userVO);
    }
}
