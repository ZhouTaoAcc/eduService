package edu.online.Entity.course.response;

import edu.online.model.response.ResultCode;

/**
 * @Classname CourseCode
 * @Description 课程信息操作码
 * @Date 2020/2/20 14:34
 * @Created by zhoutao
 */
public enum CourseCode implements ResultCode{
    COURSE_ADD_SUCCESS(true,31000,"课程添加成功！"),
    COURSE_DENIED_DELETE(false,31001,"删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(false,31002,"还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(false,31003,"创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(false,31004,"课程Id为空！"),
    COURSE_PUBLISH_VIEWERROR(false,31005,"发布课程视图出错！"),
    COURSE_MEDIS_URLISNULL(false,31101,"选择的媒资文件访问地址为空！"),
    COURSE_MEDIS_NAMEISNULL(false,31102,"选择的媒资文件名称为空！");
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CourseCode(boolean success, int code, String message){
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
