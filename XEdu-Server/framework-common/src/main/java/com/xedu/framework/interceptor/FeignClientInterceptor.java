package com.xedu.framework.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/14 15:12.
 * @Description: 自定义Feign拦截器
 */
public class FeignClientInterceptor implements RequestInterceptor {
    /**
     * 应用拦截器所用到的流程
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        try{
            // 获取请求的对象
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                // 取出request
                HttpServletRequest request = requestAttributes.getRequest();
                // 获取所有的请求头
                Enumeration<String> headerNames = request.getHeaderNames();
                if(headerNames != null){
                    // 遍历所有的请求头对象
                    while(headerNames.hasMoreElements()){
                        // 根据请求头获取对应的数据
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        template.header(name,values);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
