package edu.online.api.course;

import edu.online.Entity.course.CourseMarket;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CourseMarketControllerApi
 * @Description TODO
 * @Date 2020/2/29 13:55
 * @Created by zhoutao
 */
@Api(value = "课程营销信息", description = "查询，编辑")
public interface CourseMarketControllerApi {
    @ApiOperation("查询营销信息")
    public CourseMarket findById(String id);

    @ApiOperation("更新营销信息")
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);
}

