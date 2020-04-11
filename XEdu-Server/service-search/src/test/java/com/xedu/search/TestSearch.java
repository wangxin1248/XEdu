package com.xedu.search;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/11 21:20.
 * @Description: ElasticSearch功能测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    // 注入对应的es客户端，优先使用高等级客户端
    @Autowired
    RestHighLevelClient highLevelClient;
    @Autowired
    RestClient restClient;
}
