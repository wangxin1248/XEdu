package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/4 11:16.
 * @Description: course_pic 表对应的dao接口
 */
public interface CoursepicRepository extends JpaRepository<CoursePic,String> {
    // 删除成功返回1否则返回0
    long deleteByCourseid(String courseid);
}
