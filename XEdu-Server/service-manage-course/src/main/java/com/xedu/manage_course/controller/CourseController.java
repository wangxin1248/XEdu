package com.xedu.manage_course.controller;

import com.xedu.api.course.CourseControllerApi;
import com.xedu.framework.domain.course.*;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.ext.CourseView;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.domain.course.request.CourseListRequest;
import com.xedu.framework.domain.course.request.CoursePublishResult;
import com.xedu.framework.domain.course.response.AddCourseResult;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.utils.XcOauth2Util;
import com.xedu.framework.web.BaseController;
import com.xedu.manage_course.dao.TeachplanMapper;
import com.xedu.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 22:19.
 * @Description:
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {
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
//    @PreAuthorize("hasAuthority('course_find_list')")
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        // 调用XcOauth2Util工具类获取当前用户所属单位的id
        XcOauth2Util xcOauth2Util = new XcOauth2Util();
        XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
        String companyId = userJwt.getCompanyId();
        return courseService.findCourseListPage(companyId,page,size,courseListRequest);
    }

    // 新增课程
    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourse(@RequestBody CourseBase courseBase) {
        return courseService.addCourse(courseBase);
    }

    // 获取课程信息
    @Override
    @PreAuthorize("hasAuthority('course_get_baseinfo')")
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

    // 保存课程图片信息
    // post 中传递过来的参数使用RequestParam来接收
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        return courseService.addCoursePic(courseId,pic);
    }

    // 查询课程图片
    // 当用户拥有course_pic_list权限时执行
    @Override
    @PreAuthorize("hasAuthority('course_pic_list')")
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    // 删除课程图片
    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    // 预览课程所需模型信息
    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.courseview(id);
    }

    // 课程预览接口
    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    // 课程发布接口
    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }

    // 保存课程计划和媒资文件相关联信息
    @Override
    @PostMapping("/savemedia")
    public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
        return courseService.savemedia(teachplanMedia);
    }
}
