package edu.online.cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname GridFsConfig
 * @Description 配置GridFSBucket 用于返回基于MongoDB的GridFs文件系统的流对象
 * @Date 2020/2/15 15:21
 * @Created by zhoutao
 */
@Configuration
public class GridFsConfig {
    @Value("${spring.data.mongodb.database}") //映射到数据库
    String db;
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(db);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;//返回下载流对象
    }
}
