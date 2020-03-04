package edu.online.course.dao;

import edu.online.Entity.course.vo.CoursePlanVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname CoursePlanMapper
 * @Description 课程计划mapper
 * @Date 2020/2/29 16:36
 * @Created by zhoutao
 */
@Mapper
public interface CoursePlanMapper {
    //1、课程计划查询(返回树形结构)
    public CoursePlanVO findPlanList(String courseId);
}
