package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/18 13:25.
 * @Description: 课程发布信息表对应dao
 */
public interface CoursePubRepository extends JpaRepository<CoursePub,String> {
}
