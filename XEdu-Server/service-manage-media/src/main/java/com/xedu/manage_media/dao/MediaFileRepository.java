package com.xedu.manage_media.dao;

import com.xedu.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 媒体文件DAO
 */
public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
