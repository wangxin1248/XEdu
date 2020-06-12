package com.xedu.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/12 16:54.
 * @Description: 身份校验过滤器测试
 */
//@Component
public class LoginFilterTest extends ZuulFilter {
    /**
     * 过滤器的内容
     * pre：请求在被路由之前执行
     * routing：在路由请求时被调用
     * post：在routing和error过滤器之后调用
     * error：在处理请求时发生错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器序号，序号越小越被优先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要执行该过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的内容
     * 测试的需求：过滤所有请求，判断请求的头部信息中是否有Authorization
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
        // 从请求对象中获取头部信息
        String authorization = request.getHeader("Authorization");
        // 判断头部信息是否存在
        if(StringUtils.isEmpty(authorization)){
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
            return null;
        }
        return null;
    }
}
