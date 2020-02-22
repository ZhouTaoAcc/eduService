package edu.online.Entity.course.vo;

import edu.online.Entity.course.Catetory;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname CategoryVO
 * @Description TODO
 * @Date 2020/2/21 15:19
 * @Created by zhoutao
 */
@Data
@ToString
public class CategoryVO  extends Catetory{
    List<CategoryVO> children;
}
