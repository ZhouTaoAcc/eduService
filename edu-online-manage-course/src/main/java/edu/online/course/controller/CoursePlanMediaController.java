package edu.online.course.controller;

import edu.online.Entity.course.CourseplanMedia;
import edu.online.api.course.CoursePlanMediaControllerApi;
import edu.online.course.service.CoursePlanMediaService;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CoursePlanMediaController
 * @Description TODO
 * @Date 2020/3/16 16:48
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/media")
public class CoursePlanMediaController implements CoursePlanMediaControllerApi {
    @Autowired
    CoursePlanMediaService coursePlanMediaService;

    @Override
    @PostMapping("/add")
    public ResponseResult saveMedia(@RequestBody CourseplanMedia courseplanMedia) {
        return coursePlanMediaService.saveMedia(courseplanMedia);
    }
}
