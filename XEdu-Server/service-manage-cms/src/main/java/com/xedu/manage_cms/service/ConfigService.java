package com.xedu.manage_cms.service;

import com.xedu.framework.domain.cms.CmsConfig;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 18:00.
 * @Description: cmsConfig controller业务实现
 */
@Service
public class ConfigService {
    @Autowired
    CmsConfigRepository cmsConfigRepository;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 根据id来查找CmsConfig对象
     * @param id CmsConfig id
     * @return CmsConfig 对象
     */
    public CmsConfig getModel(String id){
        if(id == null){
            // 抛出非法异常
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 从数据库中按照id查找对象
        Optional<CmsConfig> cmsConfig = cmsConfigRepository.findById(id);
        if(cmsConfig.isPresent()){
            return cmsConfig.get();
        }
        return null;
    }
}
