package com.xedu.ucenter.dao;

import com.xedu.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 17:29.
 * @Description: XcUser表对应的dao
 */
public interface XcUserRepository extends JpaRepository<XcUser, String> {
    // 根据用户账号查询用户信息
    XcUser findByUsername(String username);
}
