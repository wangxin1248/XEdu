package com.xedu.auth.client;

import com.xedu.framework.client.XeduServiceList;
import com.xedu.framework.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 19:58.
 * @Description: 定义远程访问的接口信息
 */
@FeignClient(value = XeduServiceList.XEDU_SERVICE_UCENTER)
public interface UserClient {
    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/ucenter/getuserext")
    public XcUserExt getUserext(@RequestParam("username") String username);
}
