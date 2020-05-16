package com.xedu.search.controller;

import com.xedu.api.search.EsCourseControllerApi;
import com.xedu.framework.domain.course.CoursePub;
import com.xedu.framework.domain.course.TeachplanMediaPub;
import com.xedu.framework.domain.search.CourseSearchParam;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

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

    // 获取课程详细信息
    @Override
    @GetMapping("/getall/{id}")
    public Map<String, CoursePub> getall(@PathVariable("id") String id) {
        return esCourseService.getall(id);
    }

    // 根据课程计划查询媒资信息
    @Override
    @GetMapping(value="/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId) {
        // 将传入的课程计划id设置为数组
        String[] teacplanIds = new String[]{teachplanId};
        QueryResponseResult<TeachplanMediaPub> result = esCourseService.getmedias(teacplanIds);
        // 获取查询的数据结果
        QueryResult<TeachplanMediaPub> queryResult = result.getQueryResult();
        if(queryResult != null || queryResult.getList() != null && queryResult.getList().size()>0){
            return queryResult.getList().get(0);
        }
        return new TeachplanMediaPub();
    }
}
