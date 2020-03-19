package edu.online.learning.dao;

import edu.online.Entity.learning.LearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname LearningCourseRepository
 * @Description TODO
 * @Date 2020/3/16 13:33
 * @Created by zhoutao
 */
@Repository
public interface LearningCourseRepository extends JpaRepository<LearningCourse,String> {
    public List<LearningCourse> findByUserId(String userId);
}
