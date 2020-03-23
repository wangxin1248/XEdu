package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/23 11:20.
 * @Description: Teachplan表对应的dao实现
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    // 定义根据父节点和courseid来查询teachplan对象的方法
    public List<Teachplan> findByCourseidAndParentid(String courseId,String parentId);
}
