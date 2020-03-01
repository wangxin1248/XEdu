package com.xedu.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Xin Wang.
 * @Description: 微服务启动入口类
 * @Date:Created in 2020/3/1 20:03.
 */
@SpringBootApplication
@EntityScan("com.xedu.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages={"com.xedu.api"})//扫描接口
@ComponentScan(basePackages={"com.xedu.manage_cms"})//扫描本项目下的所有类
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
}
