package com.xedu.learning.client;

import com.xedu.framework.client.XeduServiceList;
import com.xedu.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 18:19.
 * @Description: 课程服务Feign client对象
 */
@FeignClient(value = "SERVICE-SEARCH")
public interface CourseSearchClient {
    /**
     * 所需要调用的搜索服务所对应的方法
     * @param teachplanId 课程计划id
     * @return
     */
    @GetMapping(value="/search/course/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId);
}
