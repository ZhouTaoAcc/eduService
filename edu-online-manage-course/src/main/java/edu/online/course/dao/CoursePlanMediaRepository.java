package edu.online.course.dao;

import edu.online.Entity.course.CourseplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname CoursePlanMediaRepository
 * @Description TODO
 * @Date 2020/3/16 16:34
 * @Created by zhoutao
 */
@Repository
public interface CoursePlanMediaRepository extends JpaRepository<CourseplanMedia, String> {

}
