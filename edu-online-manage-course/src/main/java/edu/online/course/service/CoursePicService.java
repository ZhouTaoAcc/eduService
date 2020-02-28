package edu.online.course.service;

import edu.online.Entity.course.CoursePic;
import edu.online.Entity.fileserver.response.FileCode;
import edu.online.course.dao.CoursePicRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname CatetoryService
 * @Description TODO
 * @Date 2020/2/23 16:03
 * @Created by zhoutao
 */
@Service
public class CoursePicService {
    @Autowired
    CoursePicRepository coursePicRepository;

    public ResponseResult addCoursePic(CoursePic coursePic) {
        if (StringUtils.isEmpty(coursePic.getPic())) {
            return new ResponseResult(FileCode.FILE_ID_NULL);
        }
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursePicById(String courseId) {
        Optional<CoursePic> pic = coursePicRepository.findById(courseId);
        if (pic.isPresent()) {
            return pic.get();
        }
        return null;
    }

    public ResponseResult deleteCoursePicById(String courseId) {
        Optional<CoursePic> pic = coursePicRepository.findById(courseId);
        if (!pic.isPresent()) {
            return new ResponseResult(CommonCode.FAIL);
        }
        coursePicRepository.deleteById(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
