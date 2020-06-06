package com.xedu.api.auth;

import com.xedu.framework.domain.ucenter.request.LoginRequest;
import com.xedu.framework.domain.ucenter.response.LoginResult;
import com.xedu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 16:20.
 * @Description: 用户认证接口
 */
@Api(value = "用户认证接口",tags = {"用户认证接口"})
public interface AuthControllerApi {
    @ApiOperation("登录")
    public LoginResult login(LoginRequest loginRequest);
    @ApiOperation("退出")
    public ResponseResult logout();
}
