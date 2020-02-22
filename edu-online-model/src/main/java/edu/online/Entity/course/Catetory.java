package edu.online.Entity.course;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname Catetory
 * @Description 树形结构
 * @Date 2020/2/21 14:55
 * @Created by zhoutao
 */
@Data
@Entity
@Table(name = "category")
@GenericGenerator(name = "jpa-assigned",strategy = "assigned") //assigned策略 主键由自己设置
public class Catetory {
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    private String id;
    private String name;
    private String label;
    private String parentid;
    private String isshow;
    private Integer orderby;
    private String isleaf;
}
