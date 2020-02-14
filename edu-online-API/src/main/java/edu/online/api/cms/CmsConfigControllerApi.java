package edu.online.api.cms;

import edu.online.Entity.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CmsConfigControllerApi
 * @Description TODO
 * @Date 2020/2/14 14:25
 * @Created by zhoutao
 */
@Api(value = "cms数据模型管理接口", description = "cms数据模型管理接口，提供增、删、改、查")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS数据模型")
    public CmsConfig findById(String id);
}
