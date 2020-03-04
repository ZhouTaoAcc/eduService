package edu.online.ucenter.controller;

import edu.online.Entity.ucenter.VO.UserVO;
import edu.online.api.ucenter.UcenterControllerApi;
import edu.online.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UcenterController
 * @Description TODO
 * @Date 2020/3/3 13:41
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/getuser")
    public UserVO getUser(String username) {
        return userService.getUser(username);
    }
}
