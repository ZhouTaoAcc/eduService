package edu.online.search.service;

import edu.online.Entity.course.CoursePub;
import edu.online.Entity.search.request.CourseSearchParam;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SearchServerService
 * @Description TODO
 * @Date 2020/3/11 15:22
 * @Created by zhoutao
 */
@Service
public class SearchServerService {
    @Value("${edu.index}")
    private String es_index;
    @Value("${edu.type}")
    private String es_type;
    @Value("${edu.source_field}")
    private String source_field;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam) {
        if (courseSearchParam == null) {
            courseSearchParam = new CourseSearchParam();
        }
        //创建索引搜索对象
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置搜索类型
        searchRequest.types(es_type);
        //创建索引搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
        String[] includes = source_field.split(",");
        searchSourceBuilder.fetchSource(includes, new String[]{});
        //创建布尔查询对象(用于多条件合并)
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //查询方式 --关键字查询(多字段匹配 multiMatchQuery)
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "courseplan")
                    .minimumShouldMatch("70%")
                    .field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //查询方式 --根据一级分类 精确查询termQuery
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt", courseSearchParam.getMt()));
        }
        //查询方式  --根据二级分类 精确查询termQuery
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {

            boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
        }
        //查询方式 --根据难度等级 精确查询termQuery
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {

            boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
        }
        //分页
        //起始记录下标
        int from = page * size;
        searchSourceBuilder.from(from).size(size);
        //设置查询方式
        searchSourceBuilder.query(boolQueryBuilder);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);
        //向搜索对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        QueryResult queryResult = new QueryResult();
        List<CoursePub> list = new ArrayList<>();
        try {
            //执行搜索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            long count = hits.getTotalHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                CoursePub coursePub = new CoursePub();
                //源文档
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //取出id
                String id = (String) sourceAsMap.get("id");
                coursePub.setId(id);
                //取出name
                String name = (String) sourceAsMap.get("name");
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null) {
                    HighlightField highlightFieldName = highlightFields.get("name");
                    if (highlightFieldName != null) {
                        Text[] fragments = highlightFieldName.fragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text text : fragments) {
                            stringBuffer.append(text);
                        }
                        name = stringBuffer.toString();
                    }
                }
                coursePub.setName(name);
                //描述
                String description = (String) sourceAsMap.get("description");
                coursePub.setDescription(description);
                //课程计划
                String courseplan = (String) sourceAsMap.get("courseplan");
                coursePub.setCourseplan(courseplan);
                //图片
                String pic = (String) sourceAsMap.get("pic");
                coursePub.setPic(pic);
                //收费规则
                String charge = (String) sourceAsMap.get("charge");
                coursePub.setCharge(charge);
                //价格
                Double price = (Double) sourceAsMap.get("price");
                coursePub.setPrice(price);
                //原价格
                Double price_old = (Double) sourceAsMap.get("price_old");
                coursePub.setPriceOld(price_old);
                //将coursePub对象放入list
                list.add(coursePub);
            }
            queryResult.setList(list);
            queryResult.setTotal(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public Map<String, CoursePub> getCourseAll(String courseId) {
        SearchRequest searchRequest = new SearchRequest("edu_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置过滤字段
        String[] includes = source_field.split(",");
        searchSourceBuilder.fetchSource(includes, new String[]{});
        //精确查询
        searchSourceBuilder.query(QueryBuilders.termQuery("id", courseId));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest);
            SearchHits responseHits = response.getHits();
            SearchHit[] hits = responseHits.getHits();
            CoursePub coursePub = new CoursePub();
            HashMap<String, CoursePub> map = new HashMap<>();
            for (SearchHit res : hits) {
                String id = res.getId();//课程id
                Map<String, Object> sourceAsMap = res.getSourceAsMap();
                String name = (String) sourceAsMap.get("name");
                String grade = (String) sourceAsMap.get("grade");
                String charge = (String) sourceAsMap.get("charge");
                String pic = (String) sourceAsMap.get("pic");
                String description = (String) sourceAsMap.get("description");
                String courseplan = (String) sourceAsMap.get("courseplan");
                coursePub.setId(id);
                coursePub.setName(name);
                coursePub.setPic(pic);
                coursePub.setCharge(charge);
                coursePub.setGrade(grade);
                coursePub.setCourseplan(courseplan);
                coursePub.setDescription(description);
                map.put(id, coursePub);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
