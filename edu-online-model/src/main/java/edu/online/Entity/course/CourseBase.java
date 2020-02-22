package edu.online.Entity.course;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @Classname CourseBase
 * @Description 课程基本信息表 使用jpa访问
 * @Date 2020/2/20 13:34
 * @Created by zhoutao
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="course_base")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")//使用jpa的uuid主键生成策略 主键必须是字符串类型
public class CourseBase {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private String name;
    private String users;
    private String mt;
    private String st;
    private String grade;
    private String studymodel;
    private String teachmode;
    private String description;
    private String status;
    @Column(name="company_id")
    private String companyId;
    @Column(name="user_id")
    private String userId;
}
