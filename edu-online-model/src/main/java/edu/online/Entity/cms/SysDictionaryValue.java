package edu.online.Entity.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Classname SysDictionaryValue
 * @Description 字典数据项
 * @Date 2020/2/21 15:45
 * @Created by zhoutao
 */
@Data
@ToString
public class SysDictionaryValue {
    @Field("sd_name")
    private String sdName;//数据项名称
    @Field("sd_id")
    private String id;//数据项id
    @Field("sd_status")
    private String sdStatus;//数据项状态 是否可用 1可用 0废弃
    @Field("sd_type")
    private String sdType;
}
