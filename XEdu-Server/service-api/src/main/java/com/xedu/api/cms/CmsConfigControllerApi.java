package com.xedu.api.cms;

import com.xedu.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 17:59.
 * @Description: cmsConfig对应操作api
 */

@Api(value = "cms页面数据模型配置管理接口，提供页面数据模型的获取操作",tags = {"cms页面数据模型接口"})
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询cms页面数据模型配置信息")
    public CmsConfig getModel(String id);
}

