package edu.online.course.dao;

import edu.online.Entity.course.CoursePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname CoursePlanRepository
 * @Description TODO
 * @Date 2020/3/1 0:06
 * @Created by zhoutao
 */
@Repository
public interface CoursePlanRepository extends JpaRepository<CoursePlan, String> {
}
