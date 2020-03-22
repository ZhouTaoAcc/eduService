package edu.online.Entity.learning.request;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Classname LearningCourseRequest
 * @Description 查询个人选课pojo类
 * @Date 2020/3/22 16:42
 * @Created by zhoutao
 */
@Data
@ToString
public class LearningCourseRequest {
    private String userId; //当前用户
    private String charge;//是否收费
    private String valid;//是否有期限
    private Date startTime;
    private Date endTime;
    private String status;
}
