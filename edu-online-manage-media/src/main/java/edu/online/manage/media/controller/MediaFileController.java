package edu.online.manage.media.controller;

import edu.online.Entity.media.request.QueryMediaFileRequest;
import edu.online.api.meida.MediaFileControllerApi;
import edu.online.manage.media.service.MediaFileService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname MediaController
 * @Description 媒资文件的信息的 增删改查 处理
 * @Date 2020/3/17 14:02
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
    @Autowired
    MediaFileService mediaFileService;

    /**
     * @return edu.online.model.response.QueryResponseResult
     * @Description 分页查询媒资文件的信息 mongodb
     * @Param [page, size, queryMediaFileRequest]
     **/
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable int page, @PathVariable int size, QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page, size, queryMediaFileRequest);
    }

    @Override
    @GetMapping("/delete/{fileId}")
    public ResponseResult deleteFile(@PathVariable String fileId) {
        return mediaFileService.deleteFile(fileId);
    }

    @Override
    @GetMapping("/process/{fileId}")
    public ResponseResult processMedia(@PathVariable String fileId) {
        return mediaFileService.processMedia(fileId);
    }

}
