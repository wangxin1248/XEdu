package com.xedu.test.freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 13:38.
 * @Description: 启动类
 */
@SpringBootApplication
public class FreemarkerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreemarkerTestApplication.class);
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
