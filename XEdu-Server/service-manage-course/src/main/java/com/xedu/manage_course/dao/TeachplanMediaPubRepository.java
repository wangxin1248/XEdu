package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/10 16:23.
 * @Description: TeachplanMediaPub 对应的DAO
 */
public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub, String> {
    // 根据courseId删除记录
    long deleteByCourseId(String courseId);
}
