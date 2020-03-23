package com.xedu.api.course;

import com.xedu.framework.domain.course.Teachplan;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 21:07.
 * @Description: 课程管理接口
 */
@Api(value = "课程管理接口，提供课程的增删改查操作",tags = {"课程管理接口"})
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("课程计划添加")
    public ResponseResult addTeachplan(Teachplan teachplan);
}
