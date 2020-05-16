package com.xedu.api.search;

import com.xedu.framework.domain.course.CoursePub;
import com.xedu.framework.domain.course.TeachplanMediaPub;
import com.xedu.framework.domain.search.CourseSearchParam;
import com.xedu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/18 17:35.
 * @Description: 课程搜索接口
 */
@Api(value = "课程搜索接口",tags = {"课程搜索接口"})
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) throws IOException;
    @ApiOperation("根据id查询课程信息")
    public Map<String,CoursePub> getall(String id);
    @ApiOperation("根据课程计划查询媒资信息")
    public TeachplanMediaPub getmedia(String teachplanId);
}
