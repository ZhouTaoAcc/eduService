package edu.online.learning.controller;

import edu.online.Entity.learning.request.LearningCourseRequest;
import edu.online.Entity.learning.response.LearningCourseResponse;
import edu.online.api.learning.LearningCourseControllerApi;
import edu.online.learning.service.LearningCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname LearningCourseController
 * @Description TODO
 * @Date 2020/3/16 15:20
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/learning/personal")
public class LearningCourseController implements LearningCourseControllerApi {
    @Autowired
    LearningCourseService learningCourseService;

    /**
     * @return edu.online.Entity.learning.response.LearningCourseResponse
     * @Description 查询个人选课
     * @Param [userId]
     **/
    @Override
    @PostMapping("/course")
    public LearningCourseResponse findLearningCourse(@RequestBody LearningCourseRequest learningCourseRequest) {
        return learningCourseService.findLearningCourse(learningCourseRequest);
    }
}
