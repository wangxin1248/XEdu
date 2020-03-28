package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 20:13.
 * @Description: 课程营销信息dao实现
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
