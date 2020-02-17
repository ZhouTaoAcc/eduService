package edu.online.exception;

import edu.online.model.response.ResultCode;
import lombok.Data;

/**
 * @Classname CustomException
 * @Description 统一处理异常
 * @Date 2020/2/13 21:01
 * @Created by zhoutao
 */
@Data
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super("错误代码："+resultCode.code()+"、错误信息："+resultCode.message());//在控制台打出
        this.resultCode = resultCode;
    }
}
