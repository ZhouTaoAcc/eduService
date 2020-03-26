package edu.online.Entity.learning;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Classname LearningRecord
 * @Description TODO
 * @Date 2020/3/25 15:41
 * @Created by zhoutao
 */
@Data
@ToString
@Entity
@Table(name = "learning_record")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class LearningRecord {
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    private String id;
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "courseplan_id")
    private String courseplanId;
    @Column(name = "study_time")
    private String studyTime;
    private Integer isover;

}
