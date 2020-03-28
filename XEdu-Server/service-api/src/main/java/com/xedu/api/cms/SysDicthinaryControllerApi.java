package com.xedu.api.cms;

import com.xedu.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:52.
 * @Description: 系统数据字典获取接口
 */
@Api(value = "cms系统数据字典获取接口，提供系统数据字典的获取操作",tags = {"cms系统数据字典获取接口"})
public interface SysDicthinaryControllerApi {
    //数据字典
    @ApiOperation(value="数据字典查询接口")
    public SysDictionary getByType(String type);
}
