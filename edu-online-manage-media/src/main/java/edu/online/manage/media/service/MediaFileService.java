package edu.online.manage.media.service;

import edu.online.Entity.media.MediaFile;
import edu.online.Entity.media.MediaFileProcess_m3u8;
import edu.online.Entity.media.request.QueryMediaFileRequest;
import edu.online.manage.media.dao.MediaFileRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.model.response.ResponseResult;
import edu.online.utils.HlsVideoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Classname MediaService
 * @Description TODO
 * @Date 2020/3/17 14:02
 * @Created by zhoutao
 */
@Service
public class MediaFileService {
    @Value("${edu-media.ffmpeg-path}")
    String ffmpeg_path; //外部程序的地址

    //上传文件根目录
    @Value("${edu-media.video-location}")
    String serverPath;

    @Autowired
    MediaFileRepository mediaFileRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        if (queryMediaFileRequest == null) {
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "uploadTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Query query = new Query();
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())) {
            Pattern pattern = Pattern.compile("^.*" + queryMediaFileRequest.getFileOriginalName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("fileOriginalName").regex(pattern));
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getTag())) {
            Pattern pattern = Pattern.compile("^.*" + queryMediaFileRequest.getTag() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("tag").regex(pattern));
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())) {
            query.addCriteria(Criteria.where("processStatus").is(queryMediaFileRequest.getProcessStatus()));
        }
        long count = mongoTemplate.count(query, MediaFile.class);
        List<MediaFile> mediaFiles = mongoTemplate.find(query.with(pageable), MediaFile.class);
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(count);
        queryResult.setList(mediaFiles);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public ResponseResult deleteFile(String fileId) {
        Optional<MediaFile> mediaFile = mediaFileRepository.findById(fileId);
        if (mediaFile.isPresent()) {
            MediaFile mediaFile1 = mediaFile.get();
            mediaFileRepository.deleteById(fileId);
            String videoFolder_path = serverPath + mediaFile1.getFilePath();
            //从本地删除对应的媒资文件
            Boolean aBoolean = this.DeleteFileDir(videoFolder_path);
            if (aBoolean) {
                return new ResponseResult(CommonCode.SUCCESS);
            }
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //递归删除文件夹
    private Boolean DeleteFileDir(String videoFolder_path) {
        File file = new File(videoFolder_path);
        if (file.isFile()) {
             return file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                return   file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    DeleteFileDir(files[i].getAbsolutePath());
                }
                return  file.delete();
            }
        }
    }

    /**
     * @return edu.online.model.response.ResponseResult
     * @Description 手动处理非avi类型视频
     * @Param [fileId]
     **/
    public ResponseResult processMedia(String fileId) {
        Optional<MediaFile> presentFile = mediaFileRepository.findById(fileId);
        if (presentFile.isPresent()) {
            MediaFile mediaFile = presentFile.get();
            //上传的视频文件路径
            String mp4_path = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
            //生成m3u8_name文件名称
            String m3u8_name = mediaFile.getFileId() + ".m3u8";
            //生成m3u8文件所在目录
            String m3u8folder_name = serverPath + mediaFile.getFilePath() + "hls/";
            HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path, mp4_path, m3u8_name, m3u8folder_name);
            String m3u8Res = hlsVideoUtil.generateM3u8();
            if (!m3u8Res.equals("success")) {
                //处理失败
                mediaFile.setProcessStatus("303003");
                MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
                mediaFileProcess_m3u8.setErrormsg(m3u8Res);
                mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
                mediaFileRepository.save(mediaFile);
                return new ResponseResult(CommonCode.FAIL);
            }
            //处理成功
            mediaFile.setProcessStatus("303002");
            //获取ts文件列表
            List<String> ts_list = hlsVideoUtil.get_ts_list();
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setTslist(ts_list);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            //保存fileUrl（此url就是m3u8文件的相对路径 也就是视频播放的相对路径）
            String fileUrl = mediaFile.getFilePath() + "hls/" + m3u8_name;
            mediaFile.setFileUrl(fileUrl);
            mediaFileRepository.save(mediaFile);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.INVALID_PARAM);
    }
}
