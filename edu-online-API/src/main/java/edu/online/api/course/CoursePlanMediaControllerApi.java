package edu.online.api.course;

import edu.online.Entity.course.CourseplanMedia;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CoursePlanMediaControllerApi
 * @Description TODO
 * @Date 2020/3/16 16:50
 * @Created by zhoutao
 */
@Api(value = "媒质管理")
public interface CoursePlanMediaControllerApi {
    @ApiOperation("保存课程计划的媒资文件")
    public ResponseResult saveMedia(CourseplanMedia courseplanMedia);

    @ApiOperation("根据id查询课程计划的媒资")
    public CourseplanMedia findPlanMedia(String courseplanId);
}
