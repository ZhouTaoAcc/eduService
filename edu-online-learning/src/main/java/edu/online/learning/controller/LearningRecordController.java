package edu.online.learning.controller;

import edu.online.Entity.learning.LearningRecord;
import edu.online.api.learning.LearningRecordControllerApi;
import edu.online.learning.service.LearningRecordService;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname LearningCourseController
 * @Description TODO
 * @Date 2020/3/16 15:20
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/learning/record")
public class LearningRecordController implements LearningRecordControllerApi {
    @Autowired
    LearningRecordService learningRecordService;


    @Override
    @PostMapping("/save")
    public ResponseResult saveRecord(@RequestBody LearningRecord learningRecord) {
        return learningRecordService.saveRecord(learningRecord);
    }
    @Override
    @GetMapping("/get")
    public LearningRecord getRecord(LearningRecord learningRecord) {
        return learningRecordService.getRecord(learningRecord);
    }
    @Override
    @GetMapping("/getall")
    public List<LearningRecord> getAllRecord(LearningRecord learningRecord) {
        return learningRecordService.getAllRecord(learningRecord);
    }
}
