package edu.online.Entity.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/4/13.
 */
@Data
@ToString
public class CourseBaseRequest {
    //公司Id
    @ApiModelProperty("所属机构Id")
    private String companyId;
}
