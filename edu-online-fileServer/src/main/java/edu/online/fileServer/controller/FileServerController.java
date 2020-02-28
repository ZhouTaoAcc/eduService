package edu.online.fileServer.controller;

import edu.online.Entity.fileserver.response.FileServerRespone;
import edu.online.api.fileServer.FileServerControllerApi;
import edu.online.fileServer.service.FileServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname fileServerController
 * @Description TODO
 * @Date 2020/2/27 16:17
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/fileServer")
public class FileServerController implements FileServerControllerApi {
    @Autowired
    FileServerService fileServerService;

    /**
     * @return edu.online.Entity.fileserver.response.FileServerRespone
     * @Description //TODO
     * @Param [files, filetag, businesskey, metadata]
     **/
    @Override
    @PostMapping("/upload")
    public FileServerRespone upload(MultipartFile files, String filetag, String businesskey, String metadata) {
        return fileServerService.upload(files, filetag, businesskey, metadata);
    }
}
