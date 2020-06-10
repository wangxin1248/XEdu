package com.xedu.ucenter.dao;

import com.xedu.framework.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 17:34.
 * @Description: XcCompanyUser表所对应的DAO
 */
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser, String> {
    // 根据user id查询用户公司信息
    XcCompanyUser findByUserId(String userId);
}
