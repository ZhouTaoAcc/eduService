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
    @ApiModelProperty("课程名称")
    private String name;
    @ApiModelProperty("课程状态")
    private String status;
    @ApiModelProperty("所属机构Id")
    private String companyId;
}
