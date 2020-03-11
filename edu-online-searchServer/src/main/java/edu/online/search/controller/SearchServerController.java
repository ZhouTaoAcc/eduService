package edu.online.search.controller;

import edu.online.Entity.search.request.CourseSearchParam;
import edu.online.api.search.SearchServerControllerApi;
import edu.online.model.response.QueryResponseResult;
import edu.online.search.service.SearchServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SearchServerController
 * @Description TODO
 * @Date 2020/3/9 22:28
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/course/search")
public class SearchServerController implements SearchServerControllerApi {
    @Autowired
    SearchServerService searchServerService;

    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public QueryResponseResult list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam) {
        return searchServerService.list(page,size,courseSearchParam);
    }
}
