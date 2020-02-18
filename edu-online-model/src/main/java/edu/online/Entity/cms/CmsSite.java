package edu.online.Entity.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Classname CmsPage
 * @Description TODO
 * @Date 2019/12/8 17:08
 * @Created by zhoutao
 */
@Data
@ToString
@Document(collection = "cms_site")
public class CmsSite {

    //站点ID
    @Id
    private String siteId;
    //站点名称
    private String siteName;
    //站点域
    private String siteDomain;
    //站点端口
    private String sitePort;
    //站点访问地址
    private String siteWebPath;
    //站点物理路径
    private String sitePhysicalPath;
    //创建时间
    private Date siteCreateTime;

}
