package edu.online.Entity.media;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MediaFileProcess_m3u8 {
    //错误信息
    private String errormsg;
    //拆分ts分片的列表
    private List<String> tslist;

}
