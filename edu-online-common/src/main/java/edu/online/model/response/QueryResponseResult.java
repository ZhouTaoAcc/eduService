package edu.online.model.response;

import lombok.Data;

/**
 * @Classname QueryResponseResult
 * @Description 封装响应状态和响应结果（包括总记录数） 它用于通用的分页查询的返回类型
 * @Date 2019/12/7 17:25
 * @Created by zhoutao
 */
@Data
public class QueryResponseResult extends ResponseResult {
    QueryResult data;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode); //子类的初始化过程中，必须先执行父类的初始化动作   返回状态码
        this.data = queryResult; //返回响应结果
    }
}
