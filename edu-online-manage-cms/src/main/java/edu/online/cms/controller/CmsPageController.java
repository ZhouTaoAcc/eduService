package edu.online.cms.controller;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.api.cms.CmsPageControllerApi;
import edu.online.cms.service.CmsPageService;
import edu.online.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/list/{pageNo}/{pageSize}/{startDate}/{endDate}")
    public QueryResponseResult findList(@PathVariable("pageNo") int pageNo,
                                        @PathVariable("pageSize")int pageSize,
                                        @PathVariable("startDate")String d,
                                        @PathVariable("endDate")String d2,
                                        QueryPageRequest queryPageRequest){
        return  cmsPageService.findList(pageNo,pageSize,d,d2,queryPageRequest);
    }

   //添加页面
    @Override
    public CmsResponseResult addPage(CmsPage cmsPage) {
        return cmsPageService.addPage(cmsPage);
    }
}
