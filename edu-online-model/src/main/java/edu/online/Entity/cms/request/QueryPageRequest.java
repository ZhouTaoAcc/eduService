package edu.online.Entity.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QueryPageRequest
 * @Description pojo 封装类 介于view和controller之间 用于数据传递
 * @Date 2019/12/8 17:45
 * @Created by zhoutao
 */
@Data
public class QueryPageRequest {
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //模版id
    @ApiModelProperty("模版id")
    private String templateId;
    //页面ID
    @ApiModelProperty("页面ID")
    private String pageId;

    @ApiModelProperty("页面类型")
    private String pageType;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
    //....
    //查询日期
    private String startDate;
    private String endDate;
}
