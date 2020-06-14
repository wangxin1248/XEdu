package com.xedu.ucenter.service;

import com.xedu.framework.domain.ucenter.XcCompanyUser;
import com.xedu.framework.domain.ucenter.XcMenu;
import com.xedu.framework.domain.ucenter.XcUser;
import com.xedu.framework.domain.ucenter.ext.XcUserExt;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.ucenter.dao.XcCompanyUserRepository;
import com.xedu.ucenter.dao.XcMenuMapper;
import com.xedu.ucenter.dao.XcUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/10 17:37.
 * @Description: 用户中心service
 */
@Service
public class UserService {
    /**
     * 注入所需要的dao
     */
    @Autowired
    XcUserRepository xcUserRepository;
    @Autowired
    XcCompanyUserRepository xcCompanyUserRepository;
    @Autowired
    XcMenuMapper xcMenuMapper;

    /**
     * 根据用户账号获取用户的扩展信息
     * @param username 用户账号
     * @return 用户扩展信息对象
     */
    public XcUserExt getUserExt(String username){
        // 对账号信息进行合法性验证
        if(StringUtils.isEmpty(username)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }

        // 根据用户账号获取用户对象
        XcUser xcUser = findXcUserByUsername(username);
        // 判断是否查询到对象信息
        if(xcUser == null){
            return null;
        }
        // 根据用户id查询用户所属公司信息
        String userid = xcUser.getId();
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userid);

        // 根据用户id查询所属权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userid);

        // 构建XcUserExt对象
        XcUserExt userExt = new XcUserExt();
        // 将用户信息保存到扩展信息对象中
        BeanUtils.copyProperties(xcUser,userExt);
        // 将公司信息保存到扩展信息对象中
        if(xcCompanyUser != null){
            userExt.setCompanyId(xcCompanyUser.getCompanyId());
        }
        // 将用户权限信息保存到用户信息对象中去
        userExt.setPermissions(xcMenus);

        // 返回扩展对象
        return userExt;
    }

    /**
     * 根据用户账号获取用户的信息
     * @param username
     * @return
     */
    private XcUser findXcUserByUsername(String username) {
        return xcUserRepository.findByUsername(username);
    }
}
