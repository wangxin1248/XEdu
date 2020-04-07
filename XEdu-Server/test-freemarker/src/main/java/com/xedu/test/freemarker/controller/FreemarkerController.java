package com.xedu.test.freemarker.controller;

import com.xedu.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 13:33.
 * @Description: Freemarker练习controller
 */
@RequestMapping("/freemarker")
@Controller// 普通的请求处理controller
public class FreemarkerController {
    @Autowired
    RestTemplate restTemplate;

    // map为向整个页面传递的数据
    @RequestMapping("/test1")
    public String test1(Map<String,Object> map){
        // 设置需要传入页面的数据
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setName("张三");
        student2.setName("李四");
        student1.setAge(18);
        student2.setAge(19);
        student1.setMoney(2000.1f);
        student2.setMoney(20f);
        student1.setBirthday(new Date());
        student2.setBirthday(new Date());
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);
        Map<String,Student> stuMap = new HashMap<>();
        stuMap.put("student1",student1);
        stuMap.put("student2",student2);
        // 向模型传递数据
        map.put("students",list);
        map.put("student1",student1);
        map.put("stuMap",stuMap);
        // 返回模版文件名称
        return "test01";
    }

    // 测试生成轮播图模版页面
    @RequestMapping("/banner")
    public String index_banner(Map<String,Object> map){
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map res = responseEntity.getBody();
        map.putAll(res);
        return "index_banner";
    }

    // 测试生成课程详情模版页面
    @RequestMapping("/course")
    public String course(Map<String,Object> map){
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:31200/course/courseview/4028e581617f945f01617f9dabc40000", Map.class);
        Map res = responseEntity.getBody();
        map.putAll(res);
        return "course";
    }
}
