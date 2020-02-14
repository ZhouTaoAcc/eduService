package edu.online.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Classname ResponseResult
 * @Description 返回操作信息
 * @Date 2019/12/7 17:00
 * @Created by zhoutao
 */
@Data
@NoArgsConstructor
@ToString
public class ResponseResult implements  Response {
    //操作是否成功
    boolean success = SUCCESS;
    //操作代码
    int code = SUCCESS_CODE;
    //提示信息
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }
}
