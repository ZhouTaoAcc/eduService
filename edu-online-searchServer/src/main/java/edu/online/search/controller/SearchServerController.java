package edu.online.search.controller;

import edu.online.Entity.course.CoursePub;
import edu.online.Entity.course.CourseplanMedia;
import edu.online.Entity.search.request.CourseSearchParam;
import edu.online.api.search.SearchServerControllerApi;
import edu.online.model.response.QueryResponseResult;
import edu.online.search.service.SearchServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SearchServerController
 * @Description TODO
 * @Date 2020/3/9 22:28
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/search/course")
public class SearchServerController implements SearchServerControllerApi {
    @Autowired
    SearchServerService searchServerService;

    /**
     * @return edu.online.model.response.QueryResponseResult
     * @Description 课程ES搜索服务 分页查询
     * @Param [page, size, courseSearchParam]
     **/
    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public QueryResponseResult list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam) {
        return searchServerService.list(page, size, courseSearchParam);
    }

    /**
     * @return java.util.Map<java.lang.String , edu.online.Entity.course.CoursePub>
     * @Description 根据课程id 从ES中查询课程的全部信息
     * @Param [id]
     **/
    @Override
    @GetMapping("/getall/{id}")
    public  CoursePub getCourseAll(@PathVariable("id") String courseId) {
        return searchServerService.getCourseAll(courseId);
    }

    /**
     * @return edu.online.Entity.course.CourseplanMedia
     * @Description 根据课程计划id 从es中查询课程计划的媒资
     * @Param [courseplanId]
     **/
    @Override
    @GetMapping("/getmedia/{id}")
    public CourseplanMedia getmedia(@PathVariable("id") String courseplanId) {
        return searchServerService.getmedia(courseplanId);
    }

}
