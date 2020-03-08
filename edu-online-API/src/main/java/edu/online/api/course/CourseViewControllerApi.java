package edu.online.api.course;

import edu.online.Entity.course.response.CourseResponseResult;
import edu.online.Entity.course.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CourseViewControllerApi
 * @Description TODO
 * @Date 2020/3/6 19:05
 * @Created by zhoutao
 */
@Api(value = "课程视图", description = "用于课程预览")
public interface CourseViewControllerApi {
    @ApiOperation("课程视图")
    public CourseVO courseView(String courseId);
    @ApiOperation("课程预览")
    public CourseResponseResult coursePreView(String courseId);
    @ApiOperation("课程发布")
    public CourseResponseResult coursePublish(String courseId) throws Exception;
}
