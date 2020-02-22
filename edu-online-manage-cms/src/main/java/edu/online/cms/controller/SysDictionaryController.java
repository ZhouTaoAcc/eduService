package edu.online.cms.controller;

import edu.online.Entity.cms.SysDictionary;
import edu.online.Entity.cms.request.QueryDictionaryRequest;
import edu.online.api.cms.SysDictionaryControllerApi;
import edu.online.cms.service.SysDictionaryService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname SysDictionaryController
 * @Description TODO
 * @Date 2020/2/21 15:57
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/system/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    SysDictionaryService sysDictionaryService;

    @Override
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findList(@PathVariable int pageNo, @PathVariable int pageSize, QueryDictionaryRequest queryDictionaryRequest) {
        return sysDictionaryService.findList(pageNo,pageSize,queryDictionaryRequest);
    }

    @Override
    @GetMapping("/findByType/{type}")
    public SysDictionary findByType(@PathVariable String type) {
        return sysDictionaryService.findByType(type);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult addDictionary(@RequestBody  QueryDictionaryRequest queryDictionaryRequest) {
        return sysDictionaryService.addDictionary(queryDictionaryRequest);
    }

    @Override
    @PutMapping("update/{type}/{id}")
    public ResponseResult updateDictionary(@PathVariable String type,@PathVariable String id,@RequestBody  QueryDictionaryRequest queryDictionaryRequest) {
        return sysDictionaryService.updateDictionary(type,id,queryDictionaryRequest);
    }

    @Override
    @GetMapping("/setStatusByType/{type}/{id}")
    public ResponseResult setStatusByType(@PathVariable String type,@PathVariable String id, String status) {
        return sysDictionaryService.setStatusByType(type,id,status);
    }

    @Override
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteDictionary(@PathVariable String id) {
        return sysDictionaryService.deleteDictionary(id);
    }
}
