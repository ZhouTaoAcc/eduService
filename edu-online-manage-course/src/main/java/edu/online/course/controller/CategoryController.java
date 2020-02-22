package edu.online.course.controller;

import edu.online.Entity.course.vo.CategoryVO;
import edu.online.api.course.CategoryControllerApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CategoryController
 * @Description TODO
 * @Date 2020/2/21 15:29
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/category")
public class CategoryController implements CategoryControllerApi {
    @Override
    @GetMapping("/list")
    public CategoryVO findList() {
        return null;
    }
}
