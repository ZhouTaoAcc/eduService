package edu.online.Entity.cms;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Classname CmsPage
 * @Description TODO
 * @Date 2019/12/8 16:58
 * @Created by zhoutao
 */
@Data
@Document(collection = "cms_page")
public class CmsPage {
    //站点id
    private String siteId;
    //模板id
    private String templateId;
    //页面id
    @Id
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //访问地址
    private String pageWebPath;
    //参数
    private String pageParameter;
    //物理路径
    private String pagePhysicalPath;
    //类型（静态/动态）
    private String pageType;
    //页面模版
    private String pageTemplate;
    //页面静态化内容
    private String pageHtml;
    //状态
    private String pageStatus;
    //创建时间
    private Date pageCreateTime;
    //参数列表
    private List<CmsPageParam> pageParams;
    //静态文件Id
    private String htmlFileId;
    //数据Url
    private String dataUrl;
}
