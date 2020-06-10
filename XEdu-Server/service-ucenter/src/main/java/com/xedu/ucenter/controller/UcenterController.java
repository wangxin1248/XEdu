package com.xedu.ucenter.controller;

import com.xedu.api.ucenter.UcenterControllerApi;
import com.xedu.framework.domain.ucenter.ext.XcUserExt;
import com.xedu.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 19:21.
 * @Description: 用户中心controller
 */
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    /**
     * 导入用户service
     */
    @Autowired
    UserService userService;

    /**
     * 根据用户账号获取用户的扩展信息
     * @param username
     * @return
     */
    @Override
    @GetMapping("/getuserext")
    public XcUserExt getUserext(@RequestParam("username") String username) {
        return userService.getUserExt(username);
    }
}
