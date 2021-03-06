package edu.online.course.service;

import edu.online.Entity.course.CourseplanMedia;
import edu.online.course.dao.CoursePlanMediaRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname CoursePlanMediaService
 * @Description TODO
 * @Date 2020/3/16 16:48
 * @Created by zhoutao
 */
@Service
public class CoursePlanMediaService {
    @Autowired
    CoursePlanMediaRepository coursePlanMediaRepository;

    public ResponseResult saveMedia(CourseplanMedia courseplanMedia) {
        if (courseplanMedia == null || StringUtils.isEmpty(courseplanMedia.getCourseplanId()) || StringUtils.isEmpty(courseplanMedia.getMediaUrl())) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        coursePlanMediaRepository.save(courseplanMedia);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseplanMedia findPlanMedia(String courseplanId) {
        if (courseplanId != null) {
            Optional<CourseplanMedia> planMedia = coursePlanMediaRepository.findById(courseplanId);
          if(planMedia.isPresent()){
              return planMedia.get();
          }
        }
        return null;
    }
}
