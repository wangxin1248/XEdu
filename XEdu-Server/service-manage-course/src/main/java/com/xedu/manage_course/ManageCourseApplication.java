package com.xedu.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@EntityScan("com.xedu.framework.domain.course")//扫描实体类
@ComponentScan(basePackages={"com.xedu.api"})//扫描接口
@ComponentScan(basePackages={"com.xedu.manage_course"})
@ComponentScan(basePackages={"com.xedu.framework"})//扫描common下的所有类
public class ManageCourseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
