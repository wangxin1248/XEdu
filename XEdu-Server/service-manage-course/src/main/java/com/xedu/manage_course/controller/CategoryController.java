package com.xedu.manage_course.controller;

import com.xedu.api.course.CategoryControllerApi;
import com.xedu.framework.domain.course.ext.CategoryNode;
import com.xedu.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:09.
 * @Description: 课程分类对应的Controller
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    CategoryService categoryService;

    @Override
    @GetMapping("/list")
    public CategoryNode findList() {
        return categoryService.findList();
    }
}
