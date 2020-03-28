package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:55.
 * @Description: 系统数据字典Dao
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary,String> {
    public SysDictionary findByDType(String type);
}
