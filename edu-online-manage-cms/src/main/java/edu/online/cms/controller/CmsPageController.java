package edu.online.cms.controller;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.api.cms.CmsPageControllerApi;
import edu.online.cms.service.CmsPageService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CmsPageController
 * @Description TODO
 * @Date 2019/12/8 18:19
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    CmsPageService cmsPageService;

    //分页查询
    @Override
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findList(@PathVariable("pageNo") int pageNo,
                                        @PathVariable("pageSize") int pageSize,
                                        QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(pageNo, pageSize, queryPageRequest);
    }

    //添加页面
    @Override
    @PostMapping("/add")
    public CmsResponseResult addPage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.addPage(cmsPage);
    }

    //编辑页面
    @Override
    @PutMapping("/update/{id}")
    public CmsResponseResult updatePage(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.updatePage(id, cmsPage);
    }

    //删除页面
    @Override
    @DeleteMapping("/delete/{id}")
    public CmsResponseResult deletePage(@PathVariable("id") String id) {
        return cmsPageService.deletePage(id);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return cmsPageService.findById(id);
    }

    @Override
    @GetMapping("/releasePage/{id}")
    public ResponseResult releasePage(@PathVariable("id") String id) throws Exception {
        return cmsPageService.releasePage(id);
    }

    @Override
    @GetMapping("/updateStatusById/{id}")
    public CmsPage updateStatusById(@PathVariable("id") String id,String status) {
        return  cmsPageService.updateStatusById(id,status);
    }
}
