package edu.online.course.dao;


import com.github.pagehelper.Page;
import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.request.CourseBaseRequest;
import edu.online.Entity.course.vo.CourseBaseVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname CourseMapper
 * @Description TODO
 * @Date 2020/2/20 13:28
 * @Created by zhoutao
 */
@Mapper
public interface CourseMapper {
    //根据id查询课程信息
    CourseBase findCourseBaseById(String id);
    //分页查询课程列表（多表）
    Page<CourseBaseVO> findCourseBaseList(CourseBaseRequest courseBaseRequest);
}
