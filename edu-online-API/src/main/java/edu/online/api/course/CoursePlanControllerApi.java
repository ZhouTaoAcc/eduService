package edu.online.api.course;

import edu.online.Entity.course.CoursePlan;
import edu.online.Entity.course.vo.CoursePlanVO;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CoursePlanControllerApi
 * @Description TODO
 * @Date 2020/2/29 16:29
 * @Created by zhoutao
 */
@Api(value = "课程计划接口", description = "课程计划接口，查询课程计划接口")
public interface CoursePlanControllerApi {
    @ApiOperation("查询课程计划(树)")
    public CoursePlanVO findPlanList(String courseid);

    @ApiOperation("查询课程计划")
    public CoursePlan findPlanById(String id);

    @ApiOperation("新增课程计划")
    public ResponseResult addCoursePlan(CoursePlan coursePlan);

    @ApiOperation("删除课程计划")
    public ResponseResult deleteCoursePlanById(String id);
}
