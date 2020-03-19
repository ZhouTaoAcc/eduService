package edu.online.Entity.media.response;

import edu.online.Entity.media.MediaFile;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MediaFileResponseResult
 * @Description 媒资文件查询响应结果
 * @Date 2020/3/17 15:39
 * @Created by zhoutao
 */
@Data
@NoArgsConstructor
public class MediaFileResponseResult extends ResponseResult {
    MediaFile mediaFile;

    public MediaFileResponseResult(ResultCode resultCode, MediaFile mediaFile) {
        super(resultCode);
        this.mediaFile = mediaFile;
    }
}
