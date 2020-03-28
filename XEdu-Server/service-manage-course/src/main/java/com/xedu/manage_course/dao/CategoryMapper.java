package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 14:58.
 * @Description: Category 表对应的DAO实现
 */
@Mapper
public interface CategoryMapper {
    // 查找对应的课程类别
    public CategoryNode selectList();
}
