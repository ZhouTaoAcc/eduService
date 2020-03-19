package edu.online.Entity.learning.response;

import edu.online.Entity.course.CoursePub;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Classname LearningCourseResponse
 * @Description TODO
 * @Date 2020/3/16 15:39
 * @Created by zhoutao
 */
@NoArgsConstructor
@Data
@ToString
public class LearningCourseResponse extends ResponseResult{
    List<Map<String,CoursePub>> list;
    public LearningCourseResponse(ResultCode resultCode, List<Map<String,CoursePub>> list) {
        super(resultCode);
        this.list=list;
    }
}
