package com.xedu.manage_cms.controller;

import com.xedu.api.cms.SysDicthinaryControllerApi;
import com.xedu.framework.domain.system.SysDictionary;
import com.xedu.manage_cms.service.SysDicthinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 16:05.
 * @Description: 系统字典controller
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired
    SysDicthinaryService sysDicthinaryService;

    @Override
    @GetMapping("/get/{dType}")
    public SysDictionary getByType(@PathVariable("dType") String type) {
        return sysDicthinaryService.getByType(type);
    }
}
