package edu.online.api.meida;

import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @Classname MediaUploadControllerApi
 * @Description TODO
 * @Date 2020/3/18 13:02
 * @Created by zhoutao
 */
@Api(value = "媒资管理接口", description = "媒资管理接口，提供文件上传、处理等接口")
public interface MediaUploadControllerApi {
    //文件上传前的准备工作,校验文件是否存在
    @ApiOperation("文件上传校验")
    public ResponseResult register(String fileMd5,
                                   String fileName,
                                   Long fileSize,
                                   String mimetype,
                                   String fileExt);

    @ApiOperation("校验分块文件是否存在")
    public ResponseResult checkchunk(String fileMd5,
                                     Integer chunk,
                                     Integer chunkSize);

    @ApiOperation("上传分块")
    public ResponseResult uploadchunk(MultipartFile file,
                                      String fileMd5,
                                      Integer chunk);

    @ApiOperation("合并分块")
    public ResponseResult mergechunks(String fileMd5,
                                      String fileName,
                                      Long fileSize,
                                      String mimetype,
                                      String fileExt) throws FileNotFoundException;
}
