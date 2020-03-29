package edu.online.Entity.learning.vo;

import edu.online.Entity.learning.LearningComment;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname LearningCommentVO
 * @Description TODO
 * @Date 2020/3/29 16:32
 * @Created by zhoutao
 */
@Data
@ToString
public class LearningCommentVO extends LearningComment {
    List<LearningComment> children;
}
