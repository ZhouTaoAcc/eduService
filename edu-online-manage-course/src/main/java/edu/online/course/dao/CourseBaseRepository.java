package edu.online.course.dao;

import edu.online.Entity.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname CourseBaseRepository
 * @Description
 * @Date 2020/2/20 13:58
 * @Created by zhoutao
 */
@Repository
public interface CourseBaseRepository extends JpaRepository<CourseBase, String> {
}
