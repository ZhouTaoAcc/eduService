package edu.online.Entity.course.response;

import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname CourseResponseResult
 * @Description TODO
 * @Date 2020/3/7 16:06
 * @Created by zhoutao
 */
@Data
@NoArgsConstructor
public class CourseResponseResult extends ResponseResult {
    String url; //页面访问的路径 预览路径
    public CourseResponseResult(ResultCode resultCode, String preUrl) {
        super(resultCode);
        this.url=preUrl;
    }
}
