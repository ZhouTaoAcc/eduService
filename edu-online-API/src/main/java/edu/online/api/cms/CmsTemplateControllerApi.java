package edu.online.api.cms;

import edu.online.Entity.cms.CmsTemplate;
import edu.online.Entity.cms.request.QueryTemplateRequest;
import edu.online.Entity.cms.response.CmsTemplateResponseResult;
import edu.online.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname CmsTemplateControllerApi
 * @Description TODO
 * @Date 2020/2/14 16:38
 * @Created by zhoutao
 */
@Api(value="cms模板管理接口",description = "cms模板管理接口，提供模板的增、删、改、查")
public interface CmsTemplateControllerApi {
    //1.模板查询
    @ApiOperation("分页查询模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="pageSize",value = "每页记录数",required=true,paramType="path",dataType="int"),
            })
    public QueryResponseResult findList(int pageNo, int pageSize,QueryTemplateRequest queryTemplateRequest);

    //2.添加模板
    @ApiOperation("添加模板")
    public CmsTemplateResponseResult addTemplate(CmsTemplate cmsPage);
    //3.添加模板
    @ApiOperation("编辑模板")
    public CmsTemplateResponseResult updateTemplate(String id,CmsTemplate cmsTemplate);
    //4.添加模板
    @ApiOperation("删除模板")
    public CmsTemplateResponseResult deleteTemplate(String id);
    //5.根据id查询模板
    @ApiOperation("查询模板")
    public CmsTemplateResponseResult findById(String id);

    /*从GridFS中对文件操作*/

    //6.上传模板文件
    @ApiOperation("上传模板文件")
    public String uploadTemplateFile(String url) ;
    //7.读取模板文件
    @ApiOperation("读取模板文件")
    public String readTemplateFile(String id,int type) throws IOException;
    //8.删除模板文件
    @ApiOperation("删除模板文件")
    public String deleteTemplateFile(String id) throws FileNotFoundException;
}
