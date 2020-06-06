package com.xedu.auth.controller;

import com.alibaba.druid.util.StringUtils;
import com.xedu.api.auth.AuthControllerApi;
import com.xedu.auth.service.AuthService;
import com.xedu.framework.domain.ucenter.ext.AuthToken;
import com.xedu.framework.domain.ucenter.request.LoginRequest;
import com.xedu.framework.domain.ucenter.response.AuthCode;
import com.xedu.framework.domain.ucenter.response.LoginResult;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 19:18.
 * @Description: 验证服务controller
 */
@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {
    /**
     * 注入配置文件中的应用id
     */
    @Value("${auth.clientId}")
    String clientId;
    /**
     * 注入配置文件中的应用密码
     */
    @Value("${auth.clientSecret}")
    String clientSecret;
    /**
     * 注入配置文件中的域名
     */
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    /**
     * 注入配置文件中的cookie有效期
     */
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;
    /**
     * 注入service
     */
    @Autowired
    AuthService authService;

    /**
     * 登录功能实现
     * @param loginRequest
     * @return
     */
    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        // 对账号和密码进行验证
        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())){
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getPassword())){
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }

        // 获取登录账号
        String username = loginRequest.getUsername();
        // 获取登录密码
        String password = loginRequest.getPassword();

        // 申请令牌
        AuthToken authToken = authService.login(username,password,clientId,clientSecret);
        // 获取用户身份令牌
        String access_token = authToken.getAccess_token();

        // 将用户身份令牌存储到cookie中
        this.saveCookie(access_token);

        // 返回登录结果
        return new LoginResult(CommonCode.SUCCESS,access_token);
    }

    /**
     * 将令牌存储到cookie中
     * @param token
     */
    private void saveCookie(String token){
        // 获取response
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // 向指定的域名写入cookie
        // HttpServletResponse response,String domain,String path, String name,
        //                                 String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    /**
     * 注销功能实现
     * @return
     */
    @Override
    public ResponseResult logout() {
        return null;
    }
}
