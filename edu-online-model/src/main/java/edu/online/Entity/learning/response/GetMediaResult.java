package edu.online.Entity.learning.response;

import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
@ToString
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {
    //视频播放地址
    String mediaUrl;
    public GetMediaResult(ResultCode resultCode, String mediaUrl){
        super(resultCode);
        this.mediaUrl = mediaUrl;
    }
}
