package edu.online.api.cms;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
/*
Swagger是全球最大的OpenAPI规范（OAS）API开发工具框架，支持从设计文档到测试和部署的整个API生命周期的开发
@Api：修饰整个类，描述Controller的作用
@ApiOperation：描述一个类的一个方法，或者说一个接口
@ApiParam：单个参数描述
@ApiModel：用对象来接收参数
@ApiModelProperty：用对象接收参数时，描述对象的一个字段
@ApiResponse：HTTP响应其中1个描述
@ApiResponses：HTTP响应整体描述
@ApiIgnore：使用该注解忽略这个API
@ApiError ：发生错误返回的信息 @ApiImplicitParam：一个请求参数
@ApiImplicitParams：多个请求参数
*/

/**
 * @Classname CmsPageControllerApi
 * @Description 对外统一-查询页面的接口 服务之间调用
 * @Date 2019/12/8 17:48
 * @Created by zhoutao
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //1.页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", paramType = "path", dataType = "string")
    })
    public QueryResponseResult findList(int pageNo, int pageSize, String d, String d2, QueryPageRequest queryPageRequest);

    //2.添加页面
    @ApiOperation("添加页面")
    public CmsResponseResult addPage(CmsPage cmsPage);

    //3.添加页面
    @ApiOperation("编辑页面")
    public CmsResponseResult updatePage(String id, CmsPage cmsPage);

    //4.添加页面
    @ApiOperation("删除页面")
    public CmsResponseResult deletePage(String id);

    //5.根据id查询页面
    @ApiOperation("查询页面")
    public CmsPage findById(String id);

    //6.页面预览
//    @ApiOperation("页面预览")
//    public void pagePreview(String pageId) throws IOException, TemplateException;

    //7.页面发布
    @ApiOperation("页面发布")
    public ResponseResult releasePage(String pageId) throws IOException, TemplateException;
}
