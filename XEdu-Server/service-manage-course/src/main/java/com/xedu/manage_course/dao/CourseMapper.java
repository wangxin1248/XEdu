package com.xedu.manage_course.dao;

import com.github.pagehelper.Page;
import com.xedu.framework.domain.course.CourseBase;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   // 根据id查找课程对象
   CourseBase findCourseBaseById(String id);
   // 查找课程列表，分页所需的page和size由mybatis拦截器获取，这里只需传入CourseListRequest
   Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);

}
