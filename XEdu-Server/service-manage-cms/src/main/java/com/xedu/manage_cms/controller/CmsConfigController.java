package com.xedu.manage_cms.controller;

import com.xedu.api.cms.CmsConfigControllerApi;
import com.xedu.framework.domain.cms.CmsConfig;
import com.xedu.manage_cms.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 18:05.
 * @Description:
 */
@RestController
@RequestMapping("/cms/config")// 整个controller的请求方式不用指定
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    ConfigService configService;


    @Override
    @GetMapping("/getmodel/{id}")// 标记当前请求的方式及其url
    public CmsConfig getModel(@PathVariable("id") String id) {
        return configService.getModel(id);
    }
}
