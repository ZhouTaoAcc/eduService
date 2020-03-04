package edu.online.api.ucenter;

import edu.online.Entity.ucenter.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户中心", description = "用户管理接口")
public interface UcenterControllerApi {
    @ApiOperation("根据账号查询用户信息")
    public User getUser(String username);

}
