package edu.online.learning.dao;

import edu.online.Entity.learning.LearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname LearningRecordRepository
 * @Description TODO
 * @Date 2020/3/16 13:33
 * @Created by zhoutao
 */
@Repository
public interface LearningRecordRepository extends JpaRepository<LearningRecord, String> {
    //确定唯一索引
    LearningRecord findByCourseIdAndCourseplanIdAndUserId(String courseId, String courseplanId, String userId);

    List<LearningRecord>  findByCourseIdAndUserId(String courseId, String userId);
}
