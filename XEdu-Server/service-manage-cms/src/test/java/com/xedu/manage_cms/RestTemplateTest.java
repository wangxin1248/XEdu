package com.xedu.manage_cms;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 18:25.
 * @Description: 对RestTemplate方法进行测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateTest {
    @Autowired
    RestTemplate restTemplate;

    // 测试通过网络流来获取模版对象
    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map map = responseEntity.getBody();
        System.out.println(map);
    }
}
