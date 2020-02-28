package edu.online.Entity.fileserver.response;

import edu.online.model.response.ResultCode;

/**
 * @Classname FileCode
 * @Description TODO
 * @Date 2020/2/27 16:46
 * @Created by zhoutao
 */
public enum FileCode implements ResultCode {
    FILE_UPLOAD_SUCCSEE(true, 50001, "文件上传成功！"),
    FILE_UPLOAD_FAIL(false, 50002, "文件上传失败！"),
    FILE_ID_NULL(false, 50003, "获取不到文件ID！"),
    FILE_IS_NULL(false, 50004, "上传文件为空！"),
    FILE_INITFDFSERROR(false,50010,"初始化fastDFS环境出错！"),;

    private boolean success;
    private int code;
    private String message;

    FileCode(boolean success, int code, String message) {
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
