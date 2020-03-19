package edu.online.learning.controller;

import edu.online.Entity.learning.response.LearningCourseResponse;
import edu.online.api.learning.LearningCourseControllerApi;
import edu.online.learning.service.LearningCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @Description 查询个人选课
 * @Param [userId]
 * @return edu.online.Entity.learning.response.LearningCourseResponse
 **/
    @Override
    @GetMapping("/course/{userId}")
    public LearningCourseResponse findLearningCourse(@PathVariable("userId") String userId) {
        return learningCourseService.findLearningCourse(userId);
    }
}
