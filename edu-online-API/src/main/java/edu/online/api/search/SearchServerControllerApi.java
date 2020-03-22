package edu.online.api.search;

import edu.online.Entity.course.CoursePub;
import edu.online.Entity.course.CourseplanMedia;
import edu.online.Entity.search.request.CourseSearchParam;
import edu.online.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

@Api(value = "课程搜索",description = "课程搜索",tags = {"课程搜索"})
public interface SearchServerControllerApi {
    //搜索课程信息
    @ApiOperation("课程综合搜索")
    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据课程id查询课程信息")
    public Map<String,CoursePub> getCourseAll(String courseId);

    @ApiOperation("根据课程计划id查询课程媒资信息")
    public CourseplanMedia getmedia(String courseplanId);


}
