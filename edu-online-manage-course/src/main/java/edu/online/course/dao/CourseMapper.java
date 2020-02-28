package edu.online.course.dao;


import com.github.pagehelper.Page;
import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.request.CourseBaseRequest;
import edu.online.Entity.course.vo.CategoryVO;
import edu.online.Entity.course.vo.CourseBaseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname CourseMapper
 * @Description CourseMapper提供关于课程的一系列复杂接口定义 由mapper映射文件实现
 * @Date 2020/2/20 13:28
 * @Created by zhoutao
 */
@Mapper
public interface CourseMapper {
   /**
    * @Description //TODO
    * @Param [id]
    * @return edu.online.Entity.course.CourseBase
    **/
    CourseBase findCourseBaseById(String id);

    //2、分页查询课程列表
    Page<CourseBaseVO> findCourseBaseList(@Param("courseBaseRequest") CourseBaseRequest courseBaseRequest);

    //3、课程分类查询(返回树形结构)
    public CategoryVO findList();
}
