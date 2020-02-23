package edu.online.course.service;

import edu.online.Entity.course.vo.CategoryVO;
import edu.online.course.dao.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname CatetoryService
 * @Description TODO
 * @Date 2020/2/23 16:03
 * @Created by zhoutao
 */
@Service
public class CategoryService {
    @Autowired
    CourseMapper courseMapper;
    public CategoryVO findList(){
        return courseMapper.findList();
    }
}
