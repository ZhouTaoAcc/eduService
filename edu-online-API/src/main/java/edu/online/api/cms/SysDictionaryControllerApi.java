package edu.online.api.cms;

import edu.online.Entity.cms.SysDictionary;
import edu.online.Entity.cms.request.QueryDictionaryRequest;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname SysDictionaryControllerApi
 * @Description TODO
 * @Date 2020/2/21 15:56
 * @Created by zhoutao
 */
@Api(value = "数据字典管理接口", description = "数据字典管理，增删改查")
public interface SysDictionaryControllerApi {
    @ApiOperation(value = "分页查询字典")
    public QueryResponseResult findList(int pageNo, int pageSize,QueryDictionaryRequest queryDictionaryRequest);

    @ApiOperation(value = "根据字典类型值查询字典")
    public SysDictionary findByType(String type);

    @ApiOperation(value = "新增字典")
    public ResponseResult addDictionary(QueryDictionaryRequest queryDictionaryRequest);

    @ApiOperation(value = "编辑字典")
    public ResponseResult updateDictionary(String type, String id, QueryDictionaryRequest queryDictionaryRequest);

    @ApiOperation("根据字典类型值设置状态")
    public ResponseResult setStatusByType(String type, String id,String status);
    @ApiOperation(value = "删除字典")
    public ResponseResult deleteDictionary(String id);

}
