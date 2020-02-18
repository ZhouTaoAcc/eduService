package edu.online.api.cms;

import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.request.QuerySiteRequest;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * @Classname CmsPageControllerApi
 * @Description 对外统一-查询站点的接口 服务之间调用
 * @Date 2019/12/8 17:48
 * @Created by zhoutao
 */
@Api(value="cms站点管理接口",description = "cms站点管理接口，提供站点的增、删、改、查")
public interface CmsSiteControllerApi {
    //1.站点查询
    @ApiOperation("分页查询站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="pageSize",value = "每页记录数",required=true,paramType="path",dataType="int"),
            })
    public QueryResponseResult findList(int pageNo, int pageSize, QuerySiteRequest querySiteRequest);

    //2.添加站点
    @ApiOperation("添加站点")
    public ResponseResult addSite(CmsSite cmsSite);
    //3.添加站点
    @ApiOperation("编辑站点")
    public ResponseResult updateSite(String id, CmsSite cmsSite);
    //4.添加站点
    @ApiOperation("删除站点")
    public ResponseResult deleteSite(String id);
    //5.根据id查询站点
    @ApiOperation("查询站点")
    public CmsSite findById(String id);

} 
