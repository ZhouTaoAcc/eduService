package edu.online.course.controller;

import edu.online.Entity.course.CourseMarket;
import edu.online.api.course.CourseMarketControllerApi;
import edu.online.course.service.CourseMarketService;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CourseMarketController
 * @Description TODO
 * @Date 2020/2/29 13:59
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/courseMarketInfo")
public class CourseMarketController implements CourseMarketControllerApi {
    @Autowired
    CourseMarketService courseMarketService;

    @Override
    @GetMapping("/findById/{id}")
    public CourseMarket findById(@PathVariable String id) {
        return courseMarketService.findById(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable String id, @RequestBody CourseMarket courseMarket) {
        return courseMarketService.updateCourseMarket(id, courseMarket);
    }
}
