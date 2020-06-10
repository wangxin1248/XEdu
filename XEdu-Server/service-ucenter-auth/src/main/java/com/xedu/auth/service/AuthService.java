package com.xedu.auth.service;

import com.alibaba.fastjson.JSON;
import com.xedu.framework.client.XeduServiceList;
import com.xedu.framework.domain.ucenter.ext.AuthToken;
import com.xedu.framework.domain.ucenter.response.AuthCode;
import com.xedu.framework.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 19:30.
 * @Description: 认证服务service
 */
@Service
public class AuthService {
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
     * Redis访问控制模版
     */
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * 注入redis过期时间
     */
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    /**
     * 登录功能执行用户认证申请令牌
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        // 获取令牌信息
        AuthToken authToken = this.applyToken(username, password, clientId, clientSecret);
        // 令牌为null则抛出异常
        if(authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        // 获取用户身份令牌
        String access_token = authToken.getAccess_token();
        // 存储到redis中的内容
        String jsonString = JSON.toJSONString(authToken);

        // 将令牌保存到redis中
        boolean b = this.saveToken(access_token, jsonString, tokenValiditySeconds);
        if(!b){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }

        // 返回令牌内容
        return authToken;
    }

    /**
     * 将令牌保存到redis中
     * @param access_token 用户身份令牌
     * @param content 内容就是 AuthToken 对象的内容
     * @param ttl 过期时间
     * @return 是否保存成功
     */
    private boolean saveToken(String access_token, String content, long ttl){
        // 保存的key
        String key = "user_token:"+access_token;
        // 将内容进行保存
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        // 查看保存内容的过期时间
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        // 大于0则保存成功
        return expire>0;
    }

    /**
     * 申请令牌
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret){
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
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization",httpBasic);
        // 定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // 向body中传入所需的数据
        body.add("grant_type","password");
        body.add("username", username);
        body.add("password", password);

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
        if (token_body == null ||
                token_body.get("access_token") == null ||
                token_body.get("refresh_token") == null ||
                token_body.get("jti") == null){
            // 进行错误信息验证
            if(token_body != null && token_body.get("error_description") != null){
                String error_desc = (String) token_body.get("error_description");
                // 判断错误信息的分类
                if(error_desc.indexOf("UserDetailsService returned null")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }else if(error_desc.indexOf("坏的凭证")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }
            return null;
        }
        AuthToken authToken = new AuthToken();
        // 设置用户身份令牌
        authToken.setAccess_token((String) token_body.get("jti"));
        // 设置刷新令牌
        authToken.setRefresh_token((String) token_body.get("refresh_token"));
        // 设置jwt令牌
        authToken.setJwt_token((String) token_body.get("access_token"));

        // 返回对象
        return authToken;
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
