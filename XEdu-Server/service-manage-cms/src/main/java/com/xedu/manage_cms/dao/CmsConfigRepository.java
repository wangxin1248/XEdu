package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 17:57.
 * @Description: cms_config集合操作dao
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
