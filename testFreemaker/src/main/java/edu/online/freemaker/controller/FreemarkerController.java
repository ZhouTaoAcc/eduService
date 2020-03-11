package edu.online.freemaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Classname testFreemaker
 * @Description TODO 用于测试模板+数据的效果
 * @Date 2020/2/15 13:33
 * @Created by zhoutao
 */
@RequestMapping("/freemarker")
@Controller
public class FreemarkerController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map) {
        //使用restTemplate请求轮播图的模型数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        //设置模型数据
        map.putAll(body);
        return "index_banner";
    }

    @RequestMapping("/precourse")
    public String course(Map<String, Object> map) {
        //使用restTemplate请求课程的模型数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31002/course/info/view/40288881708b11ac01708b4a54920000", Map.class);
        Map body = forEntity.getBody();
        //设置模型数据
        map.putAll(body);
        return "course";
    }
}
