package edu.online.Entity.cms.response;

import edu.online.Entity.cms.CmsTemplate;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;

/**
 * @Classname CmsTemplateResponseResult
 * @Description TODO
 * @Date 2020/2/14 16:40
 * @Created by zhoutao
 */
@Data
public class CmsTemplateResponseResult extends ResponseResult{
    CmsTemplate data;
    //List<T> data;
    public CmsTemplateResponseResult(ResultCode resultCode, CmsTemplate data) {
        super(resultCode);
        this.data = data;
    }
}
