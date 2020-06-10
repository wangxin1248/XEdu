package com.xedu.api.ucenter;

import com.xedu.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 17:02.
 * @Description: 用户中心接口定义
 */
@Api(value = "用户中心接口",tags = {"用户中心管理"})
public interface UcenterControllerApi {
    @ApiOperation("根据用户账号查询用户信息")
    public XcUserExt getUserext(String username);
}
