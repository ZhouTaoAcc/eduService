package edu.online.learning.feign;

import edu.online.Entity.course.CoursePub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Classname CourseFeign
 * @Description ES课程索引库信息
 * @Date 2020/3/16 14:54
 * @Created by zhoutao
 */
@FeignClient("edu-online-searchserver")
public interface CourseFeign {
    @GetMapping("/course/searchServer/getall/{id}")
    public Map<String, CoursePub> getCourseAll(@PathVariable("id") String courseId);
}
