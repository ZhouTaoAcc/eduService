package edu.online.course.controller;

import edu.online.Entity.course.CourseplanMedia;
import edu.online.api.course.CoursePlanMediaControllerApi;
import edu.online.course.service.CoursePlanMediaService;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CoursePlanMediaController
 * @Description 添加课程计划的媒资
 * @Date 2020/3/16 16:48
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/planmedia")
public class CoursePlanMediaController implements CoursePlanMediaControllerApi {
    @Autowired
    CoursePlanMediaService coursePlanMediaService;

    /**
     * @return edu.online.model.response.ResponseResult
     * @Description 添加课程计划绑定的媒资
     * @Param [courseplanMedia]
     **/
    @Override
    @PostMapping("/add")
    public ResponseResult saveMedia(@RequestBody CourseplanMedia courseplanMedia) {
        return coursePlanMediaService.saveMedia(courseplanMedia);
    }

    /**
     * @return edu.online.Entity.course.CourseplanMedia
     * @Description 查询课程计划绑定的媒资
     * @Param [courseplanId]
     **/
    @Override
    @GetMapping("/findById/{id}")
    public CourseplanMedia findPlanMedia(@PathVariable("id") String courseplanId) {
        return coursePlanMediaService.findPlanMedia(courseplanId);
    }

}
