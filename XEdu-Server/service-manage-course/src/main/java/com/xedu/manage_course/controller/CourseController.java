package com.xedu.manage_course.controller;

import com.xedu.api.course.CourseControllerApi;
import com.xedu.framework.domain.course.Teachplan;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_course.dao.TeachplanMapper;
import com.xedu.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 22:19.
 * @Description:
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    // 查询课程计划
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    // 添加课程计划
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachPlan(teachplan);
    }
}
