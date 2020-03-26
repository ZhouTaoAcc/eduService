package edu.online.learning.service;

import edu.online.Entity.learning.LearningRecord;
import edu.online.learning.dao.LearningRecordRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname LearningRecordService
 * @Description TODO
 * @Date 2020/3/25 16:02
 * @Created by zhoutao
 */
@Service
public class LearningRecordService {
    @Autowired
    LearningRecordRepository learningRecordRepository;

    /**
     * @return edu.online.model.response.ResponseResult
     * @Description 保存某个播放视频的进度
     * @Param [learningRecord]
     **/
    public ResponseResult saveRecord(LearningRecord learningRecord) {
        if (learningRecord.getUserId() != null && learningRecord.getCourseplanId() != null && learningRecord.getCourseId() != null) {
            LearningRecord learningRecord1 = learningRecordRepository.findByCourseIdAndCourseplanIdAndUserId(learningRecord.getCourseId(), learningRecord.getCourseplanId(), learningRecord.getUserId());
            if (learningRecord1 != null) { //记录已存在  更新即可
                learningRecord.setId(learningRecord1.getId());
                learningRecordRepository.save(learningRecord);
                return new ResponseResult(CommonCode.SUCCESS);
            }
            learningRecordRepository.save(learningRecord);//记录不存在  添加
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.INVALID_PARAM);
    }

    /**
     * @return edu.online.Entity.learning.LearningRecord
     * @Description 获取某个播放视频的进度
     * @Param [learningRecord]
     **/
    public LearningRecord getRecord(LearningRecord learningRecord) {
        if (learningRecord.getUserId() != null && learningRecord.getCourseplanId() != null && learningRecord.getCourseId() != null) {
            LearningRecord learningRecord1 = learningRecordRepository.findByCourseIdAndCourseplanIdAndUserId(learningRecord.getCourseId(), learningRecord.getCourseplanId(), learningRecord.getUserId());
            if (learningRecord1 != null) {
                return learningRecord1;
            }
        }
        return null;
    }

    /**
     * @return java.util.List<edu.online.Entity.learning.LearningRecord>
     * @Description 获取用户当前课程的进度(已经学习的课程计划)
     * @Param [learningRecord]
     **/
    public List<LearningRecord> getAllRecord(LearningRecord learningRecord) {
        if (learningRecord.getUserId() != null && learningRecord.getCourseId() != null) {
            List<LearningRecord> list = learningRecordRepository.findByCourseIdAndUserId(learningRecord.getCourseId(), learningRecord.getUserId());
            if (list != null) {
                return list;
            }
        }
        return null;
    }
}
