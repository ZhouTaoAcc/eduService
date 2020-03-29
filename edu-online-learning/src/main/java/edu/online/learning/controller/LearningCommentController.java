package edu.online.learning.controller;

import edu.online.Entity.learning.LearningComment;
import edu.online.Entity.learning.request.LearningCommentRequest;
import edu.online.api.learning.LearningCommentControllerApi;
import edu.online.learning.service.LearningCommentService;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname LearningCommentController
 * @Description TODO
 * @Date 2020/3/16 15:20
 * @Created by zhoutao
 */
@RestController
@RequestMapping("/learning/comment")
public class LearningCommentController implements LearningCommentControllerApi {
    @Autowired
    LearningCommentService learningCommentService;

    /**
     * @return edu.online.Entity.learning.vo.LearningCommentVO
     * @Description //分页查询评论
     * @Param [learningCommentRequest]
     **/
    @GetMapping("/list/{pageNo}/{pageSize}")
    public QueryResponseResult findComment(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, LearningCommentRequest learningCommentRequest) {
        return learningCommentService.findComment(pageNo,pageSize,learningCommentRequest);
    }
    @PostMapping("/add")
    public ResponseResult addNewComment(@RequestBody LearningComment learningComment) {
        return learningCommentService.addNewComment(learningComment);
    }
    @GetMapping("/delete/{id}")
    public ResponseResult deleteComment(@PathVariable("id") String id) {
        return learningCommentService.deleteComment(id);
    }
}
