package edu.online.course.controller;

import edu.online.Entity.course.response.CourseResponseResult;
import edu.online.Entity.course.vo.CourseVO;
import edu.online.api.course.CourseViewControllerApi;
import edu.online.course.service.CourseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CourseViewController
 * @Description TODO
 * @Date 2020/3/6 19:12
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/info")
public class CourseViewController implements CourseViewControllerApi {
    @Autowired
    CourseViewService courseViewService;
    //课程视图
    @Override
    @GetMapping("/view/{courseId}")
    public CourseVO courseView(@PathVariable String courseId) {
        return courseViewService.getCourseView(courseId);
    }
    //课程预览
    @Override
    @GetMapping("/preView/{courseId}")
    public CourseResponseResult coursePreView(@PathVariable String courseId) {
        return courseViewService.coursePreView(courseId);
    }
    //课程发布
    @Override
    @GetMapping("/publish/{courseId}")
    public CourseResponseResult coursePublish(@PathVariable String courseId) throws Exception {
        return courseViewService.coursePublish(courseId);
    }
}
