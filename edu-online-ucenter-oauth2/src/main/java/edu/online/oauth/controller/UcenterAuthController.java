package edu.online.oauth.controller;

import com.alibaba.fastjson.JSON;
import edu.online.Entity.ucenter.VO.UserToken;
import edu.online.Entity.ucenter.request.LoginRequest;
import edu.online.Entity.ucenter.response.AuthCode;
import edu.online.Entity.ucenter.response.LoginResponseResult;
import edu.online.api.oauth.UcenterAuthControllerApi;
import edu.online.model.response.ResponseResult;
import edu.online.oauth.service.UcenterAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UcenterAuthController
 * @Description TODO
 * @Date 2020/3/2 16:33
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/")
public class UcenterAuthController implements UcenterAuthControllerApi {
    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Autowired
    UcenterAuthService ucenterAuthService;

    @Override
    @PostMapping("/userlogin")
    public LoginResponseResult login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())) {
            return new LoginResponseResult(AuthCode.AUTH_USERNAME_NONE, null);
        }
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getPassword())) {
            return new LoginResponseResult(AuthCode.AUTH_PASSWORD_NONE, null);
        }
        //账号
        String username = loginRequest.getUsername();
        //密码
        String password = loginRequest.getPassword();

        String userToken = ucenterAuthService.login(username, password, clientId, clientSecret);
        if(userToken.equals("0")){
            return new LoginResponseResult(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL, null);
        }
        if(userToken.equals("1")){
            return new LoginResponseResult(AuthCode.AUTH_CREDENTIAL_ERROR, null);
        }
        if(userToken.equals("2")){
            return new LoginResponseResult(AuthCode.AUTH_ACCOUNT_NOTEXISTS, null);
        }
        if(userToken.equals("3")){
            return new LoginResponseResult(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL, null);
        }
        return new LoginResponseResult(AuthCode.AUTH_LOGIN_SUCCESS, JSON.parseObject(userToken, UserToken.class));
    }

    @Override
    public ResponseResult logout() {
        return null;
    }
}
