package edu.online.Entity.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QueryDictionaryRequest
 * @Description TODO
 * @Date 2020/2/21 16:27
 * @Created by zhoutao
 */
@Data
public class QueryDictionaryRequest {
    @ApiModelProperty("字典名称")
    private String dName;
    @ApiModelProperty("字典类型")
    private String dType;

    private String sdType;
    private String sdName;
    private String sdId;
    private String sdStatus;


}
