package edu.online.Entity.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Classname SysDictionary
 * @Description 数据字典实体
 * @Date 2020/2/21 15:41
 * @Created by zhoutao
 */
@Data
@ToString
@Document(collection = "sys_dictionary")
public class SysDictionary {
    @Id
    private String id;
    @Field("d_name")
    private String dName;//字典名称
    @Field("d_type")
    private String dType;//字典类型值  不重复相当于主键
    @Field("d_value")
    private List<SysDictionaryValue> children;//字典数据项数组  起名为children 前端渲染需要
}
