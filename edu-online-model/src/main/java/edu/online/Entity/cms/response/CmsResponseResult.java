package edu.online.Entity.cms.response;

import edu.online.Entity.cms.CmsPage;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;

/**
 * @Classname CmsResponseResult
 * @Description 页面cms操作 返回的响应结果
 * @Date 2020/2/11 18:30
 * @Created by zhoutao
 */
public class CmsResponseResult extends ResponseResult {
    CmsPage cmsPage;

    public CmsResponseResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
