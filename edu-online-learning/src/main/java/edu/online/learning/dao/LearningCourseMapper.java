package edu.online.learning.dao;

import edu.online.Entity.learning.LearningCourse;
import edu.online.Entity.learning.request.LearningCourseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname LearningCourseMapper
 * @Description TODO
 * @Date 2020/3/22 17:19
 * @Created by zhoutao
 */
@Mapper
public interface LearningCourseMapper {
    List<LearningCourse> findLearningCourse(@Param("learningCourseRequest") LearningCourseRequest learningCourseRequest);
}
