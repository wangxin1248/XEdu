package com.xedu.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 23:22.
 * @Description: pageservice 测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    PageService pageService;
    @Test
    public void getPageHtml(){
        String html = pageService.getPageHtml("5e6da7493e82437c7c617b01");
        System.out.println(html);
    }
}
