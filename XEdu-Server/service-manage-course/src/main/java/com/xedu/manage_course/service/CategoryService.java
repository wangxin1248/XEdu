package com.xedu.manage_course.service;

import com.xedu.framework.domain.course.ext.CategoryNode;
import com.xedu.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:06.
 * @Description: Category 业务实现
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查找所有的课程类别，并封装为CategoryNode对象
     * @return CategoryNode对象
     */
    public CategoryNode findList(){
        return categoryMapper.selectList();
    }
}
