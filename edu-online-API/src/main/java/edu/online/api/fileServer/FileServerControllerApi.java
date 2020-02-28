package edu.online.api.fileServer;

import edu.online.Entity.fileserver.response.FileServerRespone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname FileServerControllerApi
 * @Description TODO
 * @Date 2020/2/27 16:20
 * @Created by zhoutao
 */
@Api(value = "文件服务接口", description = "提供文件的上传下载服务")
public interface FileServerControllerApi {
    @ApiOperation("上传文件接口")
    public FileServerRespone upload(MultipartFile multipartFile,
                                    String filetag,
                                    String businesskey,
                                    String metadata);
}
