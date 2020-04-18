package com.xedu.search.controller;

import com.xedu.api.search.EsCourseControllerApi;
import com.xedu.framework.domain.course.CoursePub;
import com.xedu.framework.domain.search.CourseSearchParam;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/18 20:36.
 * @Description: 课程搜索对应的Controller
 */
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    EsCourseService esCourseService;

    // 课程搜索接口
    @Override
    @GetMapping(value="/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") int page,@PathVariable("size") int size, CourseSearchParam courseSearchParam) throws IOException {
        return esCourseService.list(page,size,courseSearchParam);
    }
}
