package edu.online.Entity.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Classname CourseMarket
 * @Description TODO
 * @Date 2020/2/29 13:49
 * @Created by zhoutao
 */
@Data
@Entity
@Table(name = "course_market")
@ToString
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseMarket {
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    private String id;
    private String charge;
    private String valid;
    private String qq;
    private Double price;
    @Column(name = "price_old")
    private Double priceOld;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
}
