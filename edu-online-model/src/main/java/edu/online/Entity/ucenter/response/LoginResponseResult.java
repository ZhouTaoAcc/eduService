package edu.online.Entity.ucenter.response;

import edu.online.Entity.ucenter.VO.UserToken;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;

/**
 * @Classname LoginResponseResult
 * @Description 登录返回的响应结果
 * @Date 2020/3/2 14:50
 * @Created by zhoutao
 */
@Data
public class LoginResponseResult extends ResponseResult {
    String access_token;//访问token就是短令牌，用户身份令牌
    String refresh_token;//刷新token
    String jwt_token;//jwt令牌

    public LoginResponseResult(ResultCode resultCode, UserToken token) {
        super(resultCode);
        if (token == null || ("".equals(token))) {
            return;
        }
        this.access_token = token.getAccess_token();
        this.refresh_token = token.getRefresh_token();
        this.jwt_token = token.getJwt_token();
    }
}
