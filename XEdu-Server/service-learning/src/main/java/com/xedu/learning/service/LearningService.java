package com.xedu.learning.service;

import com.xedu.framework.domain.course.TeachplanMediaPub;
import com.xedu.framework.domain.learning.XcLearningCourse;
import com.xedu.framework.domain.learning.response.GetMediaResult;
import com.xedu.framework.domain.learning.response.LearningCode;
import com.xedu.framework.domain.task.XcTask;
import com.xedu.framework.domain.task.XcTaskHis;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.learning.client.CourseSearchClient;
import com.xedu.learning.dao.XcLearningCourseRepository;
import com.xedu.learning.dao.XcTaskHisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 18:14.
 * @Description:
 */
@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;
    @Autowired
    XcLearningCourseRepository xcLearningCourseRepository;
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

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

    /**
     * 添加选课
     * @param courseId
     * @param userId
     * @param valid
     * @param startTime
     * @param endTime
     * @param xcTask
     * @return
     */
    @Transactional(rollbackFor = {})
    public ResponseResult addCourse(String courseId, String userId, String valid, Date startTime, Date endTime, XcTask xcTask){
        // 判断输入参数的合法性
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        if(StringUtils.isEmpty(userId)){
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERISNULL);
        }
        if(xcTask == null || StringUtils.isEmpty(xcTask.getId())){
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
        // 查询历史任务
        Optional<XcTaskHis> xcTaskHisOptional = xcTaskHisRepository.findById(xcTask.getId());
        // 假如已经存在历史任务信息则直接返回选课成功
        if(xcTaskHisOptional.isPresent()){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        // 保存选课信息
        XcLearningCourse xcLearningCourse = xcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);
        if(xcLearningCourse == null){
            // 没有选课记录则新增一条选课记录
            xcLearningCourse = new XcLearningCourse();
            xcLearningCourse.setUserId(userId);
            xcLearningCourse.setCourseId(courseId);
            xcLearningCourse.setValid(valid);
            xcLearningCourse.setStartTime(startTime);
            xcLearningCourse.setEndTime(endTime);
            xcLearningCourse.setStatus("501001");
            xcLearningCourseRepository.save(xcLearningCourse);
        }else{
            // 已经存在选课信息则更新选课记录
            xcLearningCourse.setValid(valid);
            xcLearningCourse.setStartTime(startTime);
            xcLearningCourse.setEndTime(endTime);
            xcLearningCourse.setStatus("501001");
            xcLearningCourseRepository.save(xcLearningCourse);
        }
        // 历史选课信息不存在则新增历史任务信息
        if(!xcTaskHisOptional.isPresent()){
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
