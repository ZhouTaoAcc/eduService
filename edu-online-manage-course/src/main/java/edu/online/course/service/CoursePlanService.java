package edu.online.course.service;

import edu.online.Entity.course.CoursePlan;
import edu.online.Entity.course.vo.CoursePlanVO;
import edu.online.course.dao.CoursePlanMapper;
import edu.online.course.dao.CoursePlanRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname CoursePlanService
 * @Description TODO
 * @Date 2020/2/29 16:28
 * @Created by zhoutao
 */
@Service
public class CoursePlanService {
    @SuppressWarnings("all")
    @Autowired
    CoursePlanMapper coursePlanMapper;
    @Autowired
    CoursePlanRepository coursePlanRepository;

    public CoursePlanVO findPlanList(String courseId) {
        return coursePlanMapper.findPlanList(courseId);
    }

    public ResponseResult addCoursePlan(CoursePlan coursePlan) {
        if (coursePlan == null) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        coursePlanRepository.save(coursePlan);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public CoursePlan findPlanById( String id) {
        Optional<CoursePlan> byId = coursePlanRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }
    public ResponseResult deleteCoursePlanById( String id) {
        if(StringUtils.isEmpty(id)){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        coursePlanRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
