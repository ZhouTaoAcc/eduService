package edu.online.Entity.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QueryPageRequest
 * @Description 相当于VO 介于实体和controller之间
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

}
