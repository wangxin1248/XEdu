package com.xedu.filesystem.dao;

import com.xedu.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/29 20:30.
 * @Description: FileSystem数据库集合对应dao接口
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
