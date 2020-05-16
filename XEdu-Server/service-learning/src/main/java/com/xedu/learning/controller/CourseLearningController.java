package com.xedu.learning.controller;

import com.xedu.api.learning.CourseLearningControllerApi;
import com.xedu.framework.domain.learning.response.GetMediaResult;
import com.xedu.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 18:09.
 * @Description: 学习中心对应的 Controller
 */

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {
    @Autowired
    LearningService learningService;
    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getMedia(@PathVariable("courseId") String courseId,@PathVariable("teachplanId") String teachplanId) {
        return learningService.getMedia(courseId,teachplanId);
    }
}
