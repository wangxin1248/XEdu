package com.xedu.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/11 21:20.
 * @Description: ElasticSearch功能测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestElasticS {
    // 注入对应的es客户端，优先使用高等级客户端
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    // 测试删除索引
    @Test
    public void testDeleteIndex() throws IOException {
        // 删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("xedu_course");
        // 删除索引
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        // 输出删除结果
        System.out.println(response.isAcknowledged());
    }

    // 测试创建索引
    @Test
    public void testCreateIndex() throws IOException {
        // 创建索引请求对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xedu_course");
        // 设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards",1).put("number_of_replicas",0));
        // 设置映射
        createIndexRequest.mapping("{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"name\": { \n" +
                "\t\t\t\"type\": \"text\", \n" +
                "\t\t\t\"analyzer\":\"ik_max_word\", \n" +
                "\t\t\t\"search_analyzer\":\"ik_smart\" \n" +
                "\t\t\t\n" +
                "\t\t}, \n" +
                "\t\t\"description\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\":\"ik_smart\" \n" +
                "\t\t\t\n" +
                "\t\t}, \n" +
                "\t\t\"studymodel\": {\n" +
                "\t\t\t\"type\": \"keyword\" \n" +
                "\t\t\t\n" +
                "\t\t}, \n" +
                "\t\t\"price\": {\n" +
                "\t\t\t\"type\": \"float\" \n" +
                "\t\t\t\n" +
                "\t\t}, \n" +
                "\t\t\"timestamp\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\" \n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        // 创建索引
        AcknowledgedResponse response = restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    // 测试添加文档
    @Test
    public void testAddDoc() throws IOException {
        // 准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战");
        jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "201001");
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
        jsonMap.put("timestamp", dateFormat.format(new Date()));
        jsonMap.put("price", 5.6f);
        // 索引请求对象
        IndexRequest request = new IndexRequest("xedu_course");
        // 指定索引文档内容
        request.source(jsonMap);
        // 索引响应对象
        IndexResponse response = restHighLevelClient.index(request,RequestOptions.DEFAULT);
        // 获取返回结果
        System.out.println(response.getResult());
    }

    // 测试更新文档
    @Test
    public void updateDoc() throws IOException {
        // 更新请求对象
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("xedu_course");
        updateRequest.type("_doc");
        updateRequest.id("3VEBbnEBoy54DVadJWzY");
        // 更新的数据文档
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("name", "spring cloud实战")
                .endObject());
        // 执行更新操作
        UpdateResponse update = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        // 获取更新结果
        RestStatus status = update.status();
        System.out.println(status);

    }
    // 测试删除文档
    @Test
    public void testDelDoc() throws IOException {
        //删除文档id
        String id = "3VEBbnEBoy54DVadJWzY";
        //删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("xedu_course");
        deleteRequest.type("_doc");
        deleteRequest.id(id);
        //响应对象
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
        //获取响应结果
        DocWriteResponse.Result result = deleteResponse.getResult();
        System.out.println(result);
    }
}
