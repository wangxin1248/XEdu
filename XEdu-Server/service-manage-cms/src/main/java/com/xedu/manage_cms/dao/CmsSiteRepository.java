package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/14 12:35.
 * @Description: cmssite集合操作dao
 */
// 使用Spring Data Mongodb完成数据库的查询操作，本接口继承MongoRepository即可
// cmssite站点查询dao
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
