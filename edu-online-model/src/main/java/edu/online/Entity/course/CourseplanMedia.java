package edu.online.Entity.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name = "course_plan_media")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseplanMedia implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "courseplan_id")
    private String courseplanId;

    @Column(name = "media_id")
    private String mediaId;

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "media_url")
    private String mediaUrl;
    @Column(name = "courseid")
    private String courseId;

    private Date timestamp;

}
