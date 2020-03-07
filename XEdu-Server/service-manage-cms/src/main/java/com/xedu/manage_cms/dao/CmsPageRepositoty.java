package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 12:48.
 * @Description: 使用Spring Data Mongodb完成数据库的查询操作，本接口继承MongoRepository即可
 */
public interface CmsPageRepositoty extends MongoRepository<CmsPage,String> {
}
