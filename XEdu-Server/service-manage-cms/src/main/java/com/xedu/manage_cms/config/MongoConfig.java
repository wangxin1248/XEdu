package com.xedu.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 21:24.
 * @Description: MongoDb配置类
 */
@Configuration
public class MongoConfig {
    // 获取在application.yml中设置的数据库名称
    @Value("${spring.data.mongodb.database}")
    String db;

    // GridFSBucket用于打开下载流对象
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(db);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }
}
