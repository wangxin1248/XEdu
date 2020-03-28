package com.xedu.api.course;

import com.xedu.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:13.
 * @Description: 课程分类对应的api
 */
@Api(value = "课程分类管理接口，获取课程分类列表",tags = {"课程分类管理接口"})
public interface CategoryControllerApi {
    @ApiOperation("查询课程分类")
    public CategoryNode findList();
}
