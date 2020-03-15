package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 22:42.
 * @Description: cms模版dao
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
