package edu.online.Entity.cms;

/**
 * @Classname CmsConfig
 * @Description TODO 保存的数据模型 用于和模板结合生成静态化页面
 * @Date 2020/2/14 14:03
 * @Created by zhoutao
 */

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document(collection ="cms_config")
public class CmsConfig {
    @Id
    private String id;
    private String name;
    private List<CmsConfigModel>model;
}
