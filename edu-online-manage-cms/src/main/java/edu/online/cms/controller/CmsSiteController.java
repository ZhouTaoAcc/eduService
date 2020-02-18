package edu.online.cms.controller;

import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.request.QuerySiteRequest;
import edu.online.api.cms.CmsSiteControllerApi;
import edu.online.cms.service.CmsSiteService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CmsSiteController
 * @Description TODO
 * @Date 2020/2/18 13:59
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi{
    @Autowired
    private CmsSiteService cmsSiteService;
    @Override
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findList(@PathVariable int pageNo, @PathVariable int pageSize, QuerySiteRequest querySiteRequest) {
        return cmsSiteService.findList(pageNo,pageSize,querySiteRequest);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult addSite(@RequestBody CmsSite cmsSite) {
        return cmsSiteService.addSite(cmsSite);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseResult updateSite(@PathVariable String id, @RequestBody CmsSite cmsSite) {
        return cmsSiteService.updateSite(id,cmsSite);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteSite(@PathVariable String id) {
        return cmsSiteService.deleteSite(id);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CmsSite findById(@PathVariable String id) {
        return cmsSiteService.findById(id);
    }
}
