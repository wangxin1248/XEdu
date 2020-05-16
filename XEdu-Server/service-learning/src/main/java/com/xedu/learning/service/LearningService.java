package com.xedu.learning.service;

import com.xedu.framework.domain.course.TeachplanMediaPub;
import com.xedu.framework.domain.learning.response.GetMediaResult;
import com.xedu.framework.domain.learning.response.LearningCode;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.learning.client.CourseSearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 18:14.
 * @Description:
 */
@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;
    /**
     * 根据传入的课程计划id来查询对应的课程url
     * @param courseId 课程id
     * @param teachplanId 课程计划id
     * @return
     */
    public GetMediaResult getMedia(String courseId, String teachplanId) {
        // 合法性判断
        if(StringUtils.isEmpty(courseId) || StringUtils.isEmpty(teachplanId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // TODO 学生学习资格验证

        // 远程调用搜索服务
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        // 判断是否成功获取到了数据
        if(teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        // 返回数据
        return new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
    }
}
