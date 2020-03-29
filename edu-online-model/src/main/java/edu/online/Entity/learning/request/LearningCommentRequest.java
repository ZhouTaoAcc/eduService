package edu.online.Entity.learning.request;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname LearningCourseRequest
 * @Description 查询课程评论、 评论的评论
 * @Date 2020/3/22 16:42
 * @Created by zhoutao
 */
@Data
@ToString
public class LearningCommentRequest {
    private String id;
    private String username; //当前用户
    private String type;//评论的类型
    private String courseId;
    private String courseplanId;
    private String keyword;//搜索关键字  查询主题和内容
}
