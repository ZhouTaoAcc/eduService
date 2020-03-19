package edu.online.search.test;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname TestSearch
 * @Description TODO
 * @Date 2020/3/9 22:14
 * @Created by zhoutao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    @Test
    public void testDeleteIndex() throws IOException {
        //创建删除索引对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("edu_course_index");
        //创建操作索引客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行删除操作
        DeleteIndexResponse response = indices.delete(deleteIndexRequest);
        //返回响应结果 boolean
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);

    }

    @Test
    public void testAddIndex() throws IOException {
        //创建一个创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("edu_course_index");
        //设置索引库的分片和副本参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
        //设置映射
        createIndexRequest.mapping("doc", "{\n" +
                "        \"properties\": {\n" +
                "          \"description\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\",\n" +
                "            \"search_analyzer\": \"ik_smart\"\n" +
                "          },\n" +
                "          \"name\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\",\n" +
                "            \"search_analyzer\": \"ik_smart\"\n" +
                "          },\n" +
                "          \"price\": {\n" +
                "            \"type\": \"double\"\n" +
                "          },\n" +
                "          \"studymodel\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          },\n" +
                "          \"timestamp\": {\n" +
                "            \"type\": \"date\",\n" +
                "            \"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
                "          }\n" +
                "        }\n" +
                "      }", XContentType.JSON);
        //创建操作索引客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行创建操作
        CreateIndexResponse response = indices.create(createIndexRequest);
        //返回响应结果 boolean
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void testAddDoc() throws IOException {
        //文档内容
        //准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战");
        jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "201001");
//        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        jsonMap.put("timestamp","2020-02-03");
        jsonMap.put("price", 5.6f);
        //创建添加文档对象
        IndexRequest indexRequest = new IndexRequest("edu_course_index", "doc");
        //指定数据
        indexRequest.source(jsonMap);
        //通过client进行http的请求
        IndexResponse response = restHighLevelClient.index(indexRequest);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);

    }

    @Test
    public void testGetDoc() throws IOException {
        //搜索指定id的文档
        GetRequest getRequest = new GetRequest("edu_course", "doc", "pXQgwHAB3Fn_R0J5rsMa");
        GetResponse getResponse = restHighLevelClient.get(getRequest);
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
    }

    @Test
    public void testUpdateDoc() throws IOException {
        //删除指定id的文档
        UpdateRequest updateRequest = new UpdateRequest("edu_course", "doc", "pXQgwHAB3Fn_R0J5rsMa");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "java实战");
        updateRequest.doc(map);
        UpdateResponse response = restHighLevelClient.update(updateRequest);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
    }

    @Test
    public void testDeleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("edu_course", "doc", "4028e58161bd3b380161bd3bcd2f0000");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
    }

    //DSL方式搜索
    @Test
    public void testGetDoc_DSL() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("edu_course");
        //指定类型
        searchRequest.types("doc");
        //创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //matchAllQuery搜索全部
        searchSourceBuilder.query(QueryBuilders.termQuery("name","java实战"));
        //设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name", "price"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        //设置分页
        searchSourceBuilder.from(0).size(2);
        //执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);

        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] searchHits = hits.getHits();
        //遍历
        for(SearchHit hit:searchHits){
            //文档的主键
                String id = hit.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            //价格
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(price);
        }
    }
}
