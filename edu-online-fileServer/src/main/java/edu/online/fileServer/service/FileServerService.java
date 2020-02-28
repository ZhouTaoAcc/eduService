package edu.online.fileServer.service;

import com.alibaba.fastjson.JSON;
import edu.online.Entity.fileserver.FileSystem;
import edu.online.Entity.fileserver.response.FileCode;
import edu.online.Entity.fileserver.response.FileServerRespone;
import edu.online.exception.ExceptionCast;
import edu.online.fileServer.dao.FileServerRepository;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Classname FileServerService
 * @Description TODO
 * @Date 2020/2/27 19:43
 * @Created by zhoutao
 */
@Service
public class FileServerService {
    @Autowired
    FileServerRepository fileServerRepository;
    @Value("${edu.fastdfs.tracker_servers}")
    String tracker_server;
    @Value("${edu.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${edu.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${edu.fastdfs.charset}")
    String charset;

    /**
     * @return edu.online.Entity.fileserver.response.FileServerRespone
     * @Description 文件上传
     * @Param [multipartFile, filetag, businesskey, metadata]
     **/
    public FileServerRespone upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata) {
        if (multipartFile == null) {
            return new FileServerRespone(FileCode.FILE_IS_NULL, null);
        }
        //第一步：调用方法将文件上传到fastDFS中，得到一个文件id
        String fileId = fdfs_upload(multipartFile);
        if (StringUtils.isEmpty(fileId)) {
            return new FileServerRespone(FileCode.FILE_ID_NULL, null);
        }
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystem.setFileSize(multipartFile.getSize());
        if (StringUtils.isNotEmpty(metadata)) {
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        fileServerRepository.save(fileSystem);
        return new FileServerRespone(FileCode.FILE_UPLOAD_SUCCSEE, fileSystem);
    }

    /**
     * @return java.lang.String
     * @Description FastDFS上传到文件服务器 返回文件id
     * @Param [multipartFile]
     **/
    private String fdfs_upload(MultipartFile multipartFile) {
        //初始化配置
        initFastDFSConfig();
        //创建trackerClient
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient来上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //得到文件字节
            byte[] bytes = multipartFile.getBytes();
            //得到文件的原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //得到文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传文件 返回文件id
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            return fileId;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initFastDFSConfig() {
        //初始化tracker服务地址（多个tracker中间以半角逗号分隔）
        try {
            ClientGlobal.initByTrackers(tracker_server);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileCode.FILE_INITFDFSERROR);
        }
    }
}
