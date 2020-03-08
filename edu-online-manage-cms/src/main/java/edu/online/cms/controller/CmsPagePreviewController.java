package edu.online.cms.controller;

import edu.online.cms.service.CmsPageService;
import edu.online.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.OutputStream;

/**
 * @Classname CmsPagePreviewController
 * @Description TODO
 * @Date 2020/2/16 18:36
 * @Created by zhoutao
 */
@Controller //这里不能用RestController 因为RestController输出的是json数据
@RequestMapping("/cms/page")
public class CmsPagePreviewController extends BaseController {
    @Autowired
    private CmsPageService cmsPageService;
    //页面预览
    @GetMapping("/pagePreview/{id}")
    public void pagePreview(@PathVariable("id") String pageId) throws Exception {
        String html = cmsPageService.generateHtml(pageId);//返回静态化内容
        if(StringUtils.isNotEmpty(html)){
            OutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-type","text/html;charset=utf-8");
            outputStream.write(html.getBytes("utf-8"));//向浏览器写出
        }
    }
}
