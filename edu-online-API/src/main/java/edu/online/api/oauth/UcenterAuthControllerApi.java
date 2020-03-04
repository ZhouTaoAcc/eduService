package edu.online.api.oauth;

import edu.online.Entity.ucenter.request.LoginRequest;
import edu.online.Entity.ucenter.response.LoginResponseResult;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "用户认证",description = "用户认证接口")
public interface UcenterAuthControllerApi {
    @ApiOperation("登录")
    public LoginResponseResult login(LoginRequest loginRequest);

    @ApiOperation("退出")
    public ResponseResult logout();
}
