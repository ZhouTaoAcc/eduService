package edu.online.learning.test;

import edu.online.Entity.course.CoursePub;
import edu.online.Entity.learning.LearningCourse;
import edu.online.learning.dao.LearningCourseRepository;
import edu.online.learning.feign.CourseFeign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @Classname TestFindCourse
 * @Description TODO
 * @Date 2020/3/16 13:42
 * @Created by zhoutao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFindCourse {
    @Autowired
    CourseFeign courseFeign;
    @Autowired
    LearningCourseRepository learningCourseRepository;
    @Test
    public void findLearnCourse(){
        List<LearningCourse> learningCourses = learningCourseRepository.findByUserId("49");
        ListIterator<LearningCourse> iterator = learningCourses.listIterator();
        List<CoursePub> list=new ArrayList<>();
        while (iterator.hasNext())
        {
            LearningCourse learningCourse = iterator.next();
            String courseId = learningCourse.getCourseId();
           CoursePub courseAll = courseFeign.getCourseAll(courseId);
            list.add(courseAll);
        }
    }
}
