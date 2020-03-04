package edu.online.Entity.course.vo;

import edu.online.Entity.course.CoursePlan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname CoursePlanVO
 * @Description TODO
 * @Date 2020/2/29 16:32
 * @Created by zhoutao
 */
@Data
@ToString
public class CoursePlanVO extends CoursePlan {
    List<CoursePlanVO> children;
}
