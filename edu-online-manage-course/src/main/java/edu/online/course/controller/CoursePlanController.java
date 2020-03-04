package edu.online.course.controller;

import edu.online.Entity.course.CoursePlan;
import edu.online.Entity.course.vo.CoursePlanVO;
import edu.online.api.course.CoursePlanControllerApi;
import edu.online.course.service.CoursePlanService;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CoursePlanController
 * @Description 课程计划 树形结构
 * @Date 2020/2/29 16:28
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/plan")
public class CoursePlanController implements CoursePlanControllerApi {
    @Autowired
    CoursePlanService coursePlanService;
    @Override
    @GetMapping("/findList/{courseid}")
    public CoursePlanVO findPlanList(@PathVariable String courseid) {
        return coursePlanService.findPlanList(courseid);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CoursePlan findPlanById(@PathVariable String id) {
        return coursePlanService.findPlanById(id);
    }
    @Override
    @PostMapping("/add")
    public ResponseResult addCoursePlan(@RequestBody CoursePlan coursePlan) {
        return coursePlanService.addCoursePlan(coursePlan);
    }
    @Override
    @PostMapping("/delete/{id}")
    public ResponseResult deleteCoursePlanById(@PathVariable String id) {
        return coursePlanService.deleteCoursePlanById(id);
    }
}
