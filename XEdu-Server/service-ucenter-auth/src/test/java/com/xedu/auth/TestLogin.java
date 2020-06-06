package com.xedu.auth;

import com.xedu.framework.client.XeduServiceList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 16:45.
 * @Description: 测试登录申请令牌的流程
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestLogin {
    /**
     * 远程调用客户端
     */
    @Autowired
    RestTemplate restTemplate;
    /**
     * 本地负载均衡服务端，可以用来远程调用 eureka
     */
    @Autowired
    LoadBalancerClient loadBalancerClient;


    /**
     * 测试登录远程调用 spring security 并获取令牌的整个流程，这里需要进行远程调用
     */
    @Test
    public void testLogin(){
        // 从 eureka 中获取认证服务的地址（因为Spring security在认证服务中）
        // 从 eureka 中获取认证服务的一个实例对象
        ServiceInstance serviceInstance = loadBalancerClient.choose(XeduServiceList.XEDU_SERVICE_UCENTER_AUTH);
        // 获取远程服务的地址
        URI uri = serviceInstance.getUri();
        // 拼接远程调用申请令牌的地址：http://localhost:40400/auth/oauth/token
        String auth_url = uri + "/auth/oauth/token";

        // 定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        // 向header中传入所需的数据
        String httpBasic = getHttpBasic("XcWebApp", "XcWebApp");
        header.add("Authorization",httpBasic);
        // 定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // 向body中传入所需的数据
        body.add("grant_type","password");
        body.add("username","itcast");
        body.add("password","12322");

        // 构建 http 请求对象
        HttpEntity<MultiValueMap<String,String>> multiValueMapHttpEntity = new HttpEntity<>(body, header);
        // 设置 resttemplate 远程调用的时候，对400和401不让报错，正常返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // 设置过滤400和401不让报错，正常返回数据
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401){
                    super.handleError(response);
                }
            }
        });
        // 执行远程客户端网络调用获取响应对象
        // String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,Class<T> responseType, Object... uriVariables
        ResponseEntity<Map> exchange = restTemplate.exchange(auth_url, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        // 获取申请的令牌信息
        Map token_body = exchange.getBody();
        System.out.println(token_body);
    }

    /**
     * 将用户的账号和密码拼装进行base64加密
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String getHttpBasic(String clientId, String clientSecret){
        // 构建账号和密码的串
        String result = clientId + ":" + clientSecret;
        // 对串进行base64编码
        byte[] encode = Base64Utils.encode(result.getBytes());
        // 将结果拼装为指定的样式返回
        return "Basic " + new String(encode);
    }
}

