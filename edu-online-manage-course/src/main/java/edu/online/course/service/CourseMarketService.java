package edu.online.course.service;

import edu.online.Entity.course.CourseMarket;
import edu.online.course.dao.CourseMarketRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname CourseMarketService
 * @Description TODO
 * @Date 2020/2/29 14:02
 * @Created by zhoutao
 */
@Service
public class CourseMarketService {
    @Autowired
    CourseMarketRepository courseMarketRepository;

    public CourseMarket findById(String id) {
        Optional<CourseMarket> byId = courseMarketRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        if(StringUtils.isEmpty(id)){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        courseMarket.setId(id);
        courseMarketRepository.save(courseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
