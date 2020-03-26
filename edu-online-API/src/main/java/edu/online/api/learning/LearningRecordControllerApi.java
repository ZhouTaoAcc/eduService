package edu.online.api.learning;

import edu.online.Entity.learning.LearningRecord;
import edu.online.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Classname LearningRecordControllerApi
 * @Description TODO
 * @Date 2020/3/25 15:56
 * @Created by zhoutao
 */
@Api(value = "用户学习中心", description = "学习记录")
public interface LearningRecordControllerApi {
    @ApiOperation("保存某个视频进度")
    public ResponseResult saveRecord(LearningRecord learningRecord);

    @ApiOperation("获取某个视频进度")
    public LearningRecord getRecord(LearningRecord learningRecord);

    @ApiOperation("获取课程进度")
    public List<LearningRecord> getAllRecord(LearningRecord learningRecord);
}
