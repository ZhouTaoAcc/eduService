package edu.online.cms.controller;

import edu.online.Entity.cms.CmsTemplate;
import edu.online.Entity.cms.request.QueryTemplateRequest;
import edu.online.Entity.cms.response.CmsTemplateResponseResult;
import edu.online.api.cms.CmsTemplateControllerApi;
import edu.online.cms.service.CmsTemplateService;
import edu.online.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Classname CmsTemplateController
 * @Description TODO
 * @Date 2020/2/14 16:35
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
    @Autowired
    CmsTemplateService cmsTemplateService;

    @Override
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findList(@PathVariable int pageNo, @PathVariable int pageSize, QueryTemplateRequest queryTemplateRequest) {

        return   cmsTemplateService.findList(pageNo,pageSize,queryTemplateRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsTemplateResponseResult addTemplate(@RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.addTemplate(cmsTemplate);
    }

    @Override
    @PutMapping("/update/{id}")
    public CmsTemplateResponseResult updateTemplate(@PathVariable String id, @RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.updateTemplate(id,cmsTemplate);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public CmsTemplateResponseResult deleteTemplate(@PathVariable String id) {
        return cmsTemplateService.deleteTemplate(id);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CmsTemplate findById(@PathVariable String id) {
        return cmsTemplateService.findById(id);
    }

    @Override
    @GetMapping("/uploadTemplateFile")
    public String uploadTemplateFile(String url)  {
        return cmsTemplateService.uploadTemplateFile(url);
    }

    @Override
    @GetMapping("/readTemplateFile")
    public String readTemplateFile(String id,int type) throws IOException {
        return cmsTemplateService.readTemplateFile(id,type);
    }

    @Override
    @GetMapping("/deleteTemplateFile")
    public String deleteTemplateFile(String id){
        return cmsTemplateService.deleteTemplateFile(id);
    }
}
