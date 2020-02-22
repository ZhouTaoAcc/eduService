package edu.online.Entity.course.vo;

import edu.online.Entity.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * @Classname CourseBaseVO
 * @Description VO层 对实体的扩展 用于数据显示
 * @Date 2020/2/20 14:31
 * @Created by zhoutao
 */
@Data
@ToString
public class CourseBaseVO extends CourseBase {
        //课程图片
        private String pic;
}
