package com.xedu.govern.gateway.service;

import com.xedu.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/12 20:12.
 * @Description: 完成用户身份验证的主要工作
 */
@Service
public class AuthService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 从cookie查询用户身份令牌
     * @param request
     * @return
     */
    public String getUserToken(HttpServletRequest request){
        // 获取cookie数据
        Map<String, String> cookie = CookieUtil.readCookie(request, "uid");
        // 查询uid数据
        String access_token = cookie.get("uid");
        // 不存在则返回null
        if(StringUtils.isEmpty(access_token)){
            return null;
        }
        return access_token;
    }

    /**
     * 从header中查找jwt令牌
     * @param request
     * @return
     */
    public String getJwtFormHeader(HttpServletRequest request){
        // 查找jwt令牌
        String authorization = request.getHeader("Authorization");
        // 令牌不存在返回null
        if(StringUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")){
            return null;
        }
        // 返回不包含Bearer头的jwt令牌
        return authorization.substring(7);
    }

    /**
     * 从redis中查询令牌的有效期
     * @param access_token
     * @return
     */
    public long getExpire(String access_token){
        // 构建key
        String key = "user_token:"+access_token;
        Long expire = stringRedisTemplate.getExpire(key);
        return expire;
    }
}
