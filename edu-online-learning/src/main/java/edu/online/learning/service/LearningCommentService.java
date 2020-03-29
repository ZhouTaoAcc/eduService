package edu.online.learning.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.online.Entity.learning.LearningComment;
import edu.online.Entity.learning.request.LearningCommentRequest;
import edu.online.Entity.learning.vo.LearningCommentVO;
import edu.online.learning.dao.LearningCommentMapper;
import edu.online.learning.dao.LearningCommentRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
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
public class LearningCommentService {
    @Autowired
    @SuppressWarnings("all")
    LearningCommentMapper learningCommentMapper;
    @Autowired
    LearningCommentRepository learningCommentRepository;

    public QueryResponseResult findComment(int pageNo, int pageSize, LearningCommentRequest learningCommentRequest) {
        PageHelper.startPage(pageNo + 1, pageSize);

        Page<LearningCommentVO> comment = learningCommentMapper.findComment(learningCommentRequest);
        List<LearningCommentVO> list = comment.getResult();
        long total = comment.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public ResponseResult addNewComment(LearningComment learningComment) {
        LearningComment save = learningCommentRepository.save(learningComment);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public ResponseResult deleteComment(String id) {
        learningCommentRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
