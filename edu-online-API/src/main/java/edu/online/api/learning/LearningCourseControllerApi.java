package edu.online.api.learning;

import edu.online.Entity.learning.request.LearningCourseRequest;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname LearningCourseControllerApi
 * @Description TODO
 * @Date 2020/3/16 15:20
 * @Created by zhoutao
 */
@Api(value = "用户学习中心",description = "用户选课查询")
public interface LearningCourseControllerApi {
    @ApiOperation("根据用户id查看所选课程")
    public ResponseResult findLearningCourse(LearningCourseRequest learningCourseRequest);
}
