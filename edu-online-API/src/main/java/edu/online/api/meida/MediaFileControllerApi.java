package edu.online.api.meida;

import edu.online.Entity.media.request.QueryMediaFileRequest;
import edu.online.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname MediaFileControllerApi
 * @Description TODO
 * @Date 2020/3/17 14:03
 * @Created by zhoutao
 */
@Api(value = "媒资管理", description = "提供媒资文件、视频的信息的上传等接口")
public interface MediaFileControllerApi {

    @ApiOperation("分页查询媒资文件列表")
    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

}
