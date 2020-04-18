package com.xedu.api.search;

import com.xedu.framework.domain.course.CoursePub;
import com.xedu.framework.domain.search.CourseSearchParam;
import com.xedu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/18 17:35.
 * @Description: 课程搜索接口
 */
@Api(value = "课程搜索接口",tags = {"课程搜索接口"})
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) throws IOException;
}
