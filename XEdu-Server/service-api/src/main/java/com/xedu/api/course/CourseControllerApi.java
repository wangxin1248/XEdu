package com.xedu.api.course;

import com.xedu.framework.domain.course.*;
import com.xedu.framework.domain.course.ext.CategoryNode;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.ext.CourseView;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.domain.course.request.CourseListRequest;
import com.xedu.framework.domain.course.request.CoursePublishResult;
import com.xedu.framework.domain.course.response.AddCourseResult;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

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
    @ApiOperation("查询我的课程列表")
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);
    @ApiOperation("新增课程")
    public AddCourseResult addCourse(CourseBase courseBase);
    @ApiOperation("获取课程基础信息")
    public CourseBase getCourseBaseById(String courseId) throws RuntimeException;
    @ApiOperation("更新课程基础信息")
    public ResponseResult updateCourseBase(String id,CourseBase courseBase);
    @ApiOperation("获取课程营销信息")
    public CourseMarket getCourseMarketById(String courseId);
    @ApiOperation("更新课程营销信息")
    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);
    @ApiOperation("保存课程图片")
    public ResponseResult addCoursePic(String courseId,String pic);
    @ApiOperation("获取课程图片信息")
    public CoursePic findCoursePic(String courseId);
    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);
    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);
    @ApiOperation("预览课程")
    public CoursePublishResult preview(String id);
    @ApiOperation("发布课程")
    public CoursePublishResult publish(String id);
    @ApiOperation("保存媒资信息")
    public ResponseResult savemedia(TeachplanMedia teachplanMedia);
}
