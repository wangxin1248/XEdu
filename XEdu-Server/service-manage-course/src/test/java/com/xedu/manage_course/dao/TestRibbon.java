package com.xedu.manage_course.dao;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/4 21:14.
 * @Description: Ribbon功能测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRibbon {
    @Autowired
    CmsPageClient cmsPageClient;

    @Test
    public void testRibbon(){
        CmsPage cmsPage = cmsPageClient.findById("5a754adf6abb500ad05688d9");
        System.out.println(cmsPage);
    }
}
