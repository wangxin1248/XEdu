package com.xedu.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Xin Wang.
 * @Description: 微服务启动入口类
 * @Date:Created in 2020/3/1 20:03.
 */
@SpringBootApplication//声明为StringBootApplication
@EntityScan("com.xedu.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = {"com.xedu.api"})//扫描接口
@ComponentScan(basePackages = {"com.xedu.manage_cms"})//扫描本项目下的所有类
@ComponentScan(basePackages = {"com.xedu.framework"})//扫描common工程下的类
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }

    // 配置 RestTemplate，用来远程获取Template对象
    @Bean
    public RestTemplate restTemplate(){
        // RestTemplate的底层可以使用第三方的http客户端工具实现http的
        // 请求，常用的http客户端工具有Apache HttpClient、OkHttpClient等，
        // 本项目使用OkHttpClient完成http请求，
        // 原因也是因为它的性能比较出众。
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}

