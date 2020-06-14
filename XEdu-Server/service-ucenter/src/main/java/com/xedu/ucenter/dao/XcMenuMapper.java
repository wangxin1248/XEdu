package com.xedu.ucenter.dao;

import com.xedu.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/14 09:54.
 * @Description: 用户权限信息查询dao
 */
@Mapper
public interface XcMenuMapper {
    public List<XcMenu> selectPermissionByUserId(String userid);
}
