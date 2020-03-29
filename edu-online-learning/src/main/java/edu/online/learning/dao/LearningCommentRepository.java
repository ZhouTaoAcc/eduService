package edu.online.learning.dao;

import edu.online.Entity.learning.LearningComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname LearningCourseRepository
 * @Description TODO
 * @Date 2020/3/16 13:33
 * @Created by zhoutao
 */
@Repository
public interface LearningCommentRepository extends JpaRepository<LearningComment,String> {

}
