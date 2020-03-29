package edu.online.learning.dao;

import com.github.pagehelper.Page;
import edu.online.Entity.learning.request.LearningCommentRequest;
import edu.online.Entity.learning.vo.LearningCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname LearningCommentMapper
 * @Description TODO
 * @Date 2020/3/29 17:19
 * @Created by zhoutao
 */
@Mapper
public interface LearningCommentMapper {
    //分页查询评论信息
    Page<LearningCommentVO> findComment(@Param("learningCommentRequest")LearningCommentRequest learningCommentRequest);
}
