package com.xedu.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 10:57.
 * @Description: cms client工程启动类
 */
@SpringBootApplication
@EntityScan("com.xedu.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = {"com.xedu.framework"})//扫描common工程下的类
@ComponentScan(basePackages = {"com.xedu.manage_cms_client"})//扫描本项目下的类
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }
}
