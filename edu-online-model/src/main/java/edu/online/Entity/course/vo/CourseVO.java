package edu.online.Entity.course.vo;

import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.CourseMarket;
import edu.online.Entity.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Classname CourseVO
 * @Description CourseVO包含课程的所有信息（基础信息，课程图片，课程营销，课程计划等）
 * 用于课程预览 发布
 * @Date 2020/3/6 18:48
 * @Created by zhoutao
 */
@Data
@NoArgsConstructor
@ToString
public class CourseVO {
    private CourseBase courseBase; //基础信息
    private CoursePic coursePic; //课程图片
    private CourseMarket courseMarket;//课程营销
    private CoursePlanVO coursePlanVO;//课程计划
    //....
}
