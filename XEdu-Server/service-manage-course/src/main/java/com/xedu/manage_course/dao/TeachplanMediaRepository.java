package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/10 10:16.
 * @Description: TeachplanMedia 课程计划媒资文件DAO
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia, String> {
    // 根据courseId查询课程资源信息
    List<TeachplanMedia> findByCourseId(String courseId);
}
