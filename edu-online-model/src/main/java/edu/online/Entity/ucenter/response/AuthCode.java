package edu.online.Entity.ucenter.response;

import edu.online.model.response.ResultCode;
import lombok.ToString;

/**
 * @Classname AuthCode
 * @Description 登录认证操作码
 * @Date 2020/3/2 14:34
 * @Created by zhoutao
 */

@ToString
public enum AuthCode implements ResultCode {
    AUTH_LOGIN_SUCCESS(true,23000,"登录成功！"),
    AUTH_USERNAME_NONE(false,23001,"请输入账号！"),
    AUTH_PASSWORD_NONE(false,23002,"请输入密码！"),
    AUTH_VERIFYCODE_NONE(false,23003,"请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(false,23004,"账号不存在！"),
    AUTH_CREDENTIAL_ERROR(false,23005,"账号或密码错误！"),
    AUTH_LOGIN_ERROR(false,23006,"登陆过程出现异常请尝试重新操作！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,"申请令牌失败！"),
    AUTH_LOGIN_TOKEN_SAVEFAIL(false,23008,"存储令牌失败！");

    //操作代码
    boolean success;

    //操作代码
    int code;
    //提示信息
    String message;
    private AuthCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
