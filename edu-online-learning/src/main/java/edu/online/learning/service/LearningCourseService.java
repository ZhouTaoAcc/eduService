package edu.online.learning.service;

import edu.online.Entity.course.CoursePub;
import edu.online.Entity.learning.LearningCourse;
import edu.online.Entity.learning.response.LearningCode;
import edu.online.Entity.learning.response.LearningCourseResponse;
import edu.online.learning.dao.LearningCourseRepository;
import edu.online.learning.feign.CourseFeign;
import edu.online.model.response.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @Classname LearningCourseService
 * @Description TODO
 * @Date 2020/3/16 13:39
 * @Created by zhoutao
 */
@Service
public class LearningCourseService {
    @Autowired
    LearningCourseRepository learningCourseRepository;
    @SuppressWarnings("all")
    @Autowired
    CourseFeign courseFeign;

    public LearningCourseResponse findLearningCourse(String userId) {
        List<LearningCourse> learningCourses = learningCourseRepository.findByUserId(userId);
        if (learningCourses.size() <= 0) {//暂无选课信息
            return new LearningCourseResponse(LearningCode.LEARNING_No_EXITES, null);
        }
        ListIterator<LearningCourse> iterator = learningCourses.listIterator();
        List<Map<String, CoursePub>> list = new ArrayList<>();
        while (iterator.hasNext()) {
            LearningCourse learningCourse = iterator.next();
            String courseId = learningCourse.getCourseId();
            //远程调用ES搜索服务查询ES库信息
            Map<String, CoursePub> courseAll = courseFeign.getCourseAll(courseId);
            list.add(courseAll);
        }
        return new LearningCourseResponse(CommonCode.SUCCESS, list);
    }
}
