package edu.online.Entity.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname CoursePlan
 * @Description TODO
 * @Date 2020/2/29 16:15
 * @Created by zhoutao
 */
@Data
@ToString
@Entity
@Table(name="course_plan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CoursePlan {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private String pname;
    private String parentid;
    private String grade;//层级，分为1、2、3级
    private String ptype;
    private String description;
    private String courseid;
    private Integer orderby;
    private Double timelength;
    private String trylearn;
}
