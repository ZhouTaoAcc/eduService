package edu.online.course.controller;

import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.request.CourseBaseRequest;
import edu.online.api.course.CourseBaseControllerApi;
import edu.online.course.service.CourseBaseService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CourseBaseController
 * @Description TODO
 * @Date 2020/2/20 15:22
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/courseBase")
public class CourseBaseController implements CourseBaseControllerApi{
    @Autowired
    CourseBaseService courseBaseService;
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findCourseBaseList(@PathVariable int pageNo,
                                                  @PathVariable int pageSize,
                                                  CourseBaseRequest courseBaseRequest){
        return courseBaseService.findCourseBaseList(pageNo,pageSize,courseBaseRequest);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CourseBase findCourseBaseById(@PathVariable String id) {
        return courseBaseService.findCourseBaseById(id);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseBaseService.addCourseBase(courseBase);
    }
    @Override
    @PutMapping("/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable String id, @RequestBody CourseBase courseBase) {
        return courseBaseService.updateCourseBase(id,courseBase);
    }
}
