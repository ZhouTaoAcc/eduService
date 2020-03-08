package edu.online.course.feign;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CmsPageFeignClient
 * @Description TODO
 * @Date 2020/3/4 12:53
 * @Created by zhoutao
 */
@FeignClient(value = "edu-online-manage-cms") //指明要远程访问的服务名称(eureka中)
public interface CmsPageFeignClient {
    @GetMapping("/cms/page/add")    //添加页面服务接口
    public CmsResponseResult saveCmsPage(@RequestBody CmsPage cmsPage);

    @PutMapping("/cms/page/update/{id}") //更新页面服务接口
    public CmsResponseResult updateCmsPage(@PathVariable("id") String id, @RequestBody CmsPage cmsPage);

    @GetMapping("/cms/site/findById/{id}") //查询站点信息
    public CmsSite findSiteById(@PathVariable("id") String id);

    @GetMapping("/cms/page/releasePage/{id}")//发布页面
    public ResponseResult releasePage(@PathVariable("id") String id) throws Exception;

    @GetMapping("/cms/page/updateStatusById/{id}") //更新页面状态
    public CmsPage updateStatusById(@PathVariable("id") String id,@RequestParam ("status")String status) ;

    @GetMapping("/cms/template/deleteTemplateFile")//删除静态文件
    public String deleteHtmlFile(@RequestParam ("id")String id);

}
