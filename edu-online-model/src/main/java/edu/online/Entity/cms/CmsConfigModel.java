package edu.online.Entity.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @Classname CmsConfigModel
 * @Description TODO 数据模型
 * @Date 2020/2/14 14:19
 * @Created by zhoutao
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;//主键
    private String name;//具体项目名称
    private String url;//项目url
    private Map mapValue;//项目复杂值
    private String value;//项目简单值
}
