package edu.online.oauth.feign;

import edu.online.Entity.ucenter.VO.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname UserFeignClient
 * @Description TODO
 * @Date 2020/3/4 12:53
 * @Created by zhoutao
 */
@FeignClient(value = "edu-online-ucenter") //指明要远程访问的服务名称(eureka中)
public interface UserFeignClient {
    @GetMapping("/ucenter/getuser")    //服务接口
    public UserVO getUser(@RequestParam("username") String username);
}
