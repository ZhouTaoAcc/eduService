package edu.online.api.course;

import edu.online.Entity.course.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CategoryControllerApi
 * @Description 课程分类-树形结构
 * @Date 2020/2/21 15:25
 * @Created by zhoutao
 */
@Api(value = "课程分类接口", description = "课程分类接口，查询课程分类")
public interface CategoryControllerApi {
    @ApiOperation("查询课程分类")
    public CategoryVO findList();
}
