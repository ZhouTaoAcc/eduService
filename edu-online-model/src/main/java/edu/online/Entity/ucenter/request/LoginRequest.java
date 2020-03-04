package edu.online.Entity.ucenter.request;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname LoginRequest
 * @Description 用户登录pojo类
 * @Date 2020/3/2 16:18
 * @Created by zhoutao
 */
@Data
@ToString
public class LoginRequest {
    String username;
    String password;
    String verifycode;//验证码
}
