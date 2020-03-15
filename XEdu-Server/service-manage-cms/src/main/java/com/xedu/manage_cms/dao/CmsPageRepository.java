package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 12:48.
 * @Description: cmspage集合数据操作dao
 */

// 使用Spring Data Mongodb完成数据库的查询操作，本接口继承MongoRepository即可
// cmspage页面查询dao
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    // 按照pageName，siteId以及PageWebPath作为条件查询CmsPage对象
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
