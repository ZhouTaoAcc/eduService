package edu.online.api.course;

import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.request.CourseBaseRequest;
import edu.online.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CourseBaseControllerApi
 * @Description TODO
 * @Date 2020/2/20 15:27
 * @Created by zhoutao
 */
@Api(value = "课程管理接口", description = "课程管理管理接口，提供页面的增、删、改、查")
public interface CourseBaseControllerApi {
    @ApiOperation("分页查询课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "path", dataType = "int"),
           })
    public QueryResponseResult findCourseBaseList(int pageNo, int pageSize, CourseBaseRequest courseBaseRequest);

    @ApiOperation("通过Id查询课程")
    public CourseBase findCourseBaseById(String id);

}
