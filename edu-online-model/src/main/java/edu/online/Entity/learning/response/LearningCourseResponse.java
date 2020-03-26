package edu.online.Entity.learning.response;

import edu.online.Entity.course.CoursePub;
import edu.online.model.response.ResponseResult;
import edu.online.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
    List<CoursePub> list;
    public LearningCourseResponse(ResultCode resultCode, List<CoursePub> list) {
        super(resultCode);
        this.list=list;
    }
}
