package edu.online.model.response;

import lombok.ToString;

/**
 * @Classname CommonCode
 * @Description enum类型 返回结果状态实现类
 * @Date 2019/12/7 16:35
 * @Created by zhoutao
 */
@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true,10000,"操作成功！"), //CommonCode.SUCCESS 调用
    FAIL(false,11111,"操作失败！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    EXPORT_SUCCESS(true,10004,"导出文件成功！"),
    EXPORT_fAIL(false,10005,"导出文件失败！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CommonCode(boolean success,int code, String message) {
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
