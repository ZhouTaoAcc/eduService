package edu.online.api.course;

import edu.online.Entity.course.CoursePic;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CoursePicControllerApi
 * @Description TODO
 * @Date 2020/2/27 21:37
 * @Created by zhoutao
 */
@Api(value = "课程图片接口", description = "课程图片接口，查询课程分类")
public interface CoursePicControllerApi {
    @ApiOperation("添加课程图片")
    public ResponseResult addCoursePic(CoursePic coursePic);

    @ApiOperation("查找课程图片")
    public CoursePic findCoursePicById(String courseId);

    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePicById(String courseId);
}
