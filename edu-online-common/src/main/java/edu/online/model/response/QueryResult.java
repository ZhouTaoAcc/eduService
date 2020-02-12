package edu.online.model.response;

import lombok.Data;

import java.util.List;

/**
 * @Classname QueryResult
 * @Description TODO
 * @Date 2019/12/7 17:24
 * @Created by zhoutao
 */
@Data
public class QueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;
}
