package edu.online.course.controller;

import edu.online.Entity.course.CoursePic;
import edu.online.api.course.CoursePicControllerApi;
import edu.online.course.service.CoursePicService;
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
@RequestMapping("/course/coursePic")
public class CoursePicController implements CoursePicControllerApi {
    @Autowired
    CoursePicService coursePicService;

    @Override
    @PostMapping("/add")
    public ResponseResult addCoursePic(@RequestBody CoursePic coursePic) {
        return coursePicService.addCoursePic(coursePic);
    }

    @Override
    @GetMapping("/findById/{courseId}")
    public CoursePic findCoursePicById(@PathVariable  String courseId) {
        return coursePicService.findCoursePicById(courseId);
    }
    @Override
    @GetMapping("/delete/{courseId}")
    public ResponseResult deleteCoursePicById(@PathVariable  String courseId) {
        return coursePicService.deleteCoursePicById(courseId);
    }
}
