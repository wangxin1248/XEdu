package com.xedu.learning.dao;

import com.xedu.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 19:22.
 * @Description: 选课信息Dao
 */
public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse,String> {
    /**
     * 根据用户的 userId 和 courseId 来查询选课信息
     * @param userId
     * @param courseId
     * @return
     */
    XcLearningCourse findByUserIdAndCourseId(String userId,String courseId);
}
