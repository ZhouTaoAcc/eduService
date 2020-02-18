package edu.online.Entity.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QuerySiteRequest
 * @Description pojo 封装类 介于view和controller之间 用于数据传递
 * @Date 2019/12/8 17:45
 * @Created by zhoutao
 */
@Data
public class QuerySiteRequest {
    @ApiModelProperty("站点Id")
    private String siteId;
    //站点名称
    private String siteName;
    //站点域
    private String siteDomain;
    //站点访问路径
    private String siteWebPath;

}
