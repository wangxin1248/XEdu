package com.xedu.learning.mq;

import com.alibaba.fastjson.JSON;
import com.xedu.framework.domain.task.XcTask;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.learning.config.RabbitMQConfig;
import com.xedu.learning.service.LearningService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 20:04.
 * @Description: 监听选课队列任务
 */
@Component
public class ChooseCourseTask {
    @Autowired
    LearningService learningService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE)
    public void receiveChoosecourseTask(XcTask xcTask) throws ParseException {
        // 取出消息的内容
        String requestBody = xcTask.getRequestBody();
        Map map = JSON.parseObject(requestBody, Map.class);
        // 解析消息内容
        String userId = (String) map.get("userId");
        String courseId = (String) map.get("courseId");
        String valid = (String) map.get("valid");
        Date startTime = null;
        Date endTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        if(map.get("startTime")!=null){
            startTime = dateFormat.parse((String) map.get("startTime"));
        }
        if(map.get("endTime")!=null){
            endTime =dateFormat.parse((String) map.get("endTime"));
        }
        // 添加选课
        ResponseResult responseResult = learningService.addCourse(userId, courseId, valid, startTime, endTime, xcTask);
        // 添加选课成功，要向mq发送完成添加选课的消息
        if(responseResult.isSuccess()){
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE,RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY,xcTask);
        }
    }
}
