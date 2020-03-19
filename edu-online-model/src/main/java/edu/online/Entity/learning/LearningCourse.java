package edu.online.Entity.learning;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @Classname LearningCourse
 * @Description 记录用户学习的课程
 * @Date 2020/2/29 16:15
 * @Created by zhoutao
 */
@Data
@ToString
@Entity
@Table(name="learning_course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class LearningCourse implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "user_id")
    private String userId;
    private String valid;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    private String status;

}
