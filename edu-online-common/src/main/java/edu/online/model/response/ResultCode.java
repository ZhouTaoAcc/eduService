package edu.online.model.response;

/**
 * @Classname ResultCode
 * @Description 封装返回结果状态
 * @Date 2019/12/7 16:39
 * @Created by zhoutao
 */
public interface ResultCode {
    boolean success(); //操作是否成功,true为成功，false操作失败
    int code();  //操作代码
    String message(); //提示信息
}
