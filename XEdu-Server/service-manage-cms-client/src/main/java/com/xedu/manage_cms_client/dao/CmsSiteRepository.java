package com.xedu.manage_cms_client.dao;

import com.xedu.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 11:38.
 * @Description: CmsSite 页面站点数据库dao
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
