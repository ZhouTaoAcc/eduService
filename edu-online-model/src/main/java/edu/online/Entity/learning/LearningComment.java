package edu.online.Entity.learning;

/**
 * @Classname LearningComment
 * @Description 评论表  树形
 * @Date 2020/3/26 14:11
 * @Created by zhoutao
 */

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "learning_comment")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class LearningComment {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private String username;
    @Column(name = "parent_id")
    private String parentId;  //表示回复某个评论的id
    @Column(name = "comment_title")
    private String commentTitle;
    @Column(name = "comment_text")
    private String commentText;
    private String type;
    private String praise;
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "courseplan_id")
    private String courseplanId;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
}
