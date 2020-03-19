package edu.online.manage.media.client.MQ;

import com.alibaba.fastjson.JSON;
import edu.online.Entity.media.MediaFile;
import edu.online.Entity.media.MediaFileProcess_m3u8;
import edu.online.manage.media.client.dao.MediaFileRepository;
import edu.online.utils.HlsVideoUtil;
import edu.online.utils.Mp4VideoUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Classname ComsumerProcessMedia
 * @Description 消息消费者
 * @Date 2020/3/18 14:55
 * @Created by zhoutao
 */
@Component
public class ComsumerProcessMedia {
    @Value("${edu-media.ffmpeg-path}")
    String ffmpeg_path; //外部程序的地址

    //上传文件根目录
    @Value("${edu-media.video-location}")
    String serverPath;

    @Autowired
    MediaFileRepository mediaFileRepository;

    @RabbitListener(queues = "queue_media_processor")
    public void processMedia(String jsonString) {
        //1、解析消息内容，得到mediaId
        Map map = JSON.parseObject(jsonString, Map.class);
        String mediaId = map.get("mediaId").toString();
        //2、拿mediaId从数据库查询文件信息
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        if (!optional.isPresent()) {
            return;
        }
        MediaFile mediaFile = optional.get();
        String fileType = mediaFile.getFileType();
        if (!fileType.equals("avi")) {//目前只处理avi类型的视频
            mediaFile.setProcessStatus("303004");//无需处理
            mediaFileRepository.save(mediaFile);
            return;
        } else {
            //需要处理
            mediaFile.setProcessStatus("303001");//处理中
            mediaFileRepository.save(mediaFile);
        }
        //3、使用工具类将avi文件生成mp4
        //要处理的视频文件的路径
        String video_path = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
        //生成的mp4的文件名称
        String mp4_name = mediaFile.getFileId() + ".mp4";
        //生成的mp4所在的路径(这里和avi放在同一目录下面)
        String mp4folder_path = serverPath + mediaFile.getFilePath();
        //自定义工具类
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path, video_path, mp4_name, mp4folder_path);
        //进行处理
        String res = mp4VideoUtil.generateMp4();
        if (!res.equals("success")) {
            //处理失败
            mediaFile.setProcessStatus("303003");
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(res);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }
        //3、使用工具类将MP4生成m3u8和ts文件，用于播放
        //mp4文件路径
        String mp4_path = serverPath + mediaFile.getFilePath() + mp4_name;
        //生成m3u8_name文件名称
        String m3u8_name = mediaFile.getFileId() +".m3u8";
        //生成m3u8文件所在目录
        String m3u8folder_name = serverPath + mediaFile.getFilePath()+"hls/";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path,mp4_path,m3u8_name,m3u8folder_name);
        String m3u8Res = hlsVideoUtil.generateM3u8();
        if (!m3u8Res.equals("success")) {
            //处理失败
            mediaFile.setProcessStatus("303003");
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(m3u8Res);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }
        //处理成功
        mediaFile.setProcessStatus("303002");
        //获取ts文件列表
        List<String> ts_list = hlsVideoUtil.get_ts_list();
        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
        mediaFileProcess_m3u8.setTslist(ts_list);

        //保存fileUrl（此url就是m3u8文件的相对路径 也就是视频播放的相对路径）
        String fileUrl =mediaFile.getFilePath() + "hls/"+m3u8_name;
        mediaFile.setFileUrl(fileUrl);
        mediaFileRepository.save(mediaFile);
    }
}
