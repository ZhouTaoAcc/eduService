package edu.online.Entity.fileserver.response;

import edu.online.Entity.fileserver.FileSystem;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;

/**
 * @Classname FileServerRespone
 * @Description 文件服务的返回状态和结果
 * @Date 2020/2/27 16:53
 * @Created by zhoutao
 */
@Data
public class FileServerRespone extends ResponseResult {
    FileSystem data;

    public FileServerRespone(ResultCode resultCode, FileSystem fileSystem) {
        super(resultCode);
        this.data = fileSystem;
    }
}
