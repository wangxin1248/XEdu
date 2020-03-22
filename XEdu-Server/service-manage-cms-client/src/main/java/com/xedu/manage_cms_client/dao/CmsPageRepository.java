package com.xedu.manage_cms_client.dao;

import com.xedu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 11:36.
 * @Description: CmsPage 数据库操作dao
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}
