package com.xedu.manage_course.controller;

import com.xedu.api.course.CourseControllerApi;
import com.xedu.framework.domain.course.CourseBase;
import com.xedu.framework.domain.course.CourseMarket;
import com.xedu.framework.domain.course.Teachplan;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.domain.course.request.CourseListRequest;
import com.xedu.framework.domain.course.response.AddCourseResult;
import com.xedu.framework.model.response.QueryResponseResult;
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

    // 查询课程列表
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        return courseService.findCourseListPage(page,size,courseListRequest);
    }

    // 新增课程
    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourse(@RequestBody CourseBase courseBase) {
        return courseService.addCourse(courseBase);
    }

    // 获取课程信息
    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) throws RuntimeException {
        return courseService.getCourseBaseById(courseId);
    }

    // 更新课程信息
    @Override
    @PutMapping("/coursebase/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String id, @RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(id,courseBase);
    }

    // 获取课程营销信息
    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    // 更新课程营销信息
    @Override
    @PutMapping("/coursemarket/update/{courseId}")
    public ResponseResult updateCourseMarket(@PathVariable("courseId")String id, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(id,courseMarket);
    }
}
