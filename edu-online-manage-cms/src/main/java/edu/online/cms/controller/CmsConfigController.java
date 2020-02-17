package edu.online.cms.controller;

import edu.online.Entity.cms.CmsConfig;
import edu.online.api.cms.CmsConfigControllerApi;
import edu.online.cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CmsConfigController
 * @Description TODO 获取数据模型 controller
 * @Date 2020/2/14 14:29
 * @Created by zhoutao
 */
@RestController
@RequestMapping("cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private CmsConfigService cmsConfigService;
    @Override
    @GetMapping("/getModel/{id}")
    public CmsConfig findById(@PathVariable("id") String id) {
        return cmsConfigService.findById(id);
    }
}
