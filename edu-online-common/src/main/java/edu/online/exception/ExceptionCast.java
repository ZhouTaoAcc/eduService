package edu.online.exception;

import edu.online.model.response.ResultCode;

/**
 * @Classname ExceptionCast
 * @Description TODO异常抛出处理
 * @Date 2020/2/13 21:11
 * @Created by zhoutao
 */
public class ExceptionCast {
    //使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
