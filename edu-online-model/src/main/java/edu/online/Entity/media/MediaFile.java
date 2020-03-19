package edu.online.Entity.media;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Classname MediaFile
 * @Description 媒资文件的信息的模型类
 * @Date 2020/3/17 14:08
 * @Created by zhoutao
 */
@Data
@ToString
@Document(collection = "media_file")
public class MediaFile {
    /*
   文件id、名称、大小、文件类型、文件状态（未上传、上传完成、上传失败）、上传时间、视频处理方式、视频处理状态、hls_m3u8,hls_ts_list、课程视频信息（课程id、章节id）
    */
    @Id
    //文件id
    private String fileId; //也就是md5的值
    //文件名称
    private String fileName;//md5的值+ext
    //文件原始名称
    private String fileOriginalName;
    //文件路径
    private String filePath;
    //文件url
    private String fileUrl;
    //文件类型
    private String fileType;
    //mimetype
    private String mimeType;
    //文件大小
    private Long fileSize;
    //文件状态
    private String fileStatus;
    //上传时间
    private Date uploadTime;
    //处理状态
    private String processStatus;
    //hls处理（map数据）
    private MediaFileProcess_m3u8 mediaFileProcess_m3u8;

    //tag标签用于查询
    private String tag;
}
