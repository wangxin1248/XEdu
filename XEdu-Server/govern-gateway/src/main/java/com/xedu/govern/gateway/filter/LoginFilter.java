package com.xedu.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.govern.gateway.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/12 19:23.
 * @Description: 用户身份验证过滤器
 */
@Component
public class LoginFilter extends ZuulFilter {
    @Autowired
    AuthService authService;
    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤功能实现
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取请求信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取响应对象
        HttpServletResponse response = requestContext.getResponse();
        // 获取请求对象
        HttpServletRequest request = requestContext.getRequest();

        // 从cookie查询用户身份令牌
        String userToken = authService.getUserToken(request);
        if(StringUtils.isEmpty(userToken)){
            // 用户身份令牌不存在cookie中拒绝访问
            accessDenied();
            return null;
        }
        // 从header中查找jwt令牌
        String jwtFormHeader = authService.getJwtFormHeader(request);
        if(StringUtils.isEmpty(jwtFormHeader)){
            // jwt令牌不存在cookie中拒绝访问
            accessDenied();
            return null;
        }
        // 从redis中查询令牌的有效期
        long expire = authService.getExpire(userToken);
        if(expire<0){
            // redis中不存在令牌拒绝访问
            accessDenied();
            return null;
        }
        return null;
    }

    /**
     * 设置当前的访问为拒绝访问
     */
    private void accessDenied(){
        // 获取请求信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取响应对象
        HttpServletResponse response = requestContext.getResponse();
        // 设置zuul网关为拒绝访问
        requestContext.setSendZuulResponse(false);
        // 设置对应的响应状态码
        requestContext.setResponseStatusCode(200);
        // 创建响应结果对象
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        String jsonString = JSON.toJSONString(responseResult);
        // 设置响应信息
        requestContext.setResponseBody(jsonString);
        response.setContentType("application/json;charset=UTF-8");
    }
}
