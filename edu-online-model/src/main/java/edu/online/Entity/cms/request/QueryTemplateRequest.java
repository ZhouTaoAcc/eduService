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
public class QueryTemplateRequest {
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //模版id
    @ApiModelProperty("模版id")
    private String templateId;
    //模版名称
    @ApiModelProperty("模版名称")
    private String templateName;
    //模版文件id
    @ApiModelProperty("模版文件id")
    private String templateFileId;
}
