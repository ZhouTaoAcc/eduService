package edu.online.course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.request.CourseBaseRequest;
import edu.online.Entity.course.response.CourseCode;
import edu.online.Entity.course.vo.CourseBaseVO;
import edu.online.course.dao.CourseBaseRepository;
import edu.online.course.dao.CourseMapper;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CourseBaseService
 * @Description 课程基本信息业务逻辑层
 * @Date 2020/2/20 14:02
 * @Created by zhoutao
 */
@Service
public class CourseBaseService {
    @SuppressWarnings("all")
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseBaseRepository courseBaseRepository;

    /**
     * @Description 查询课程信息列表TODO
     * @Param [pageNo, pageSize, courseBaseRequest]
     * @return edu.online.model.response.QueryResponseResult
     **/
    public QueryResponseResult findCourseBaseList(int pageNo, int pageSize, CourseBaseRequest courseBaseRequest) {
        if (courseBaseRequest == null) {
            courseBaseRequest = new CourseBaseRequest();
        }
        //PageHelper设置分页
        PageHelper.startPage(pageNo, pageSize);
        //分页查询
        Page<CourseBaseVO> courseBaseVO = courseMapper.findCourseBaseList(courseBaseRequest);
        //查询列表
        List<CourseBaseVO> list = courseBaseVO.getResult();
        //总记录数
        long total = courseBaseVO.getTotal();
        //查询结果集
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * @Description //TODO
     * @Param [id]
     * @return edu.online.Entity.course.CourseBase
     **/
    public CourseBase findCourseBaseById(String id) {
        return courseMapper.findCourseBaseById(id);
    }

    /**
     * @return
     * @Description 添加课程 使用Jpa
     * @Param
     **/
    public ResponseResult addCourseBase(CourseBase courseBase) {
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CourseCode.COURSE_ADD_SUCCESS);
    }
}
