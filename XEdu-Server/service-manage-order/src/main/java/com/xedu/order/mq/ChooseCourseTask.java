package com.xedu.order.mq;

import com.xedu.framework.domain.task.XcTask;
import com.xedu.order.config.RabbitMQConfig;
import com.xedu.order.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 14:33.
 * @Description: 定时执行选择课程的任务
 */
@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    /**
     * 导入service
     */
    @Autowired
    TaskService taskService;

    /**
     * 监听完成任务队列，执行完成任务操作
     * @param xcTask
     */
    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE)
    public void receiveFinishChoosecourseTask(XcTask xcTask){
        if(xcTask != null && StringUtils.isNoneEmpty(xcTask.getId())){
            taskService.finishTask(xcTask.getId());
        }
    }

    /**
     * 定时发送选课任务
     */
    @Scheduled(cron = "0/3 * * * * *")
    public void sendChoosecourseTask(){
        // 获得当前时间之前一分钟的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<XcTask> xcTaskList = taskService.findXcTaskList(time, 100);
        System.out.println(xcTaskList);
        // 调用service将选课的任务发送给mq
        for(XcTask xcTask:xcTaskList){
            // 判断是否可以获取到该任务
            if(taskService.getTask(xcTask.getId(),1)>0){
                // 获取发送消息的交换机
                String mqExchange = xcTask.getMqExchange();
                // 获取发送消息的路由
                String mqRoutingkey = xcTask.getMqRoutingkey();
                // 执行消息发送
                taskService.publish(xcTask,mqExchange,mqRoutingkey);
            }
        }
    }


//    @Scheduled(cron = "0/3 * * * * *")// 每隔3秒去执行
//    @Scheduled(fixedRate = 3000)// 在任务开始后3秒执行下一次调度
//    @Scheduled(fixedDelay = 3000)// 在任务结束后3秒才开始执行
    public void task1(){
        LOGGER.info("==========测试定时任务1开始=========");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("==========测试定时任务1结束=========");
    }

//    @Scheduled(cron = "0/3 * * * * *")// 每隔3秒去执行
//    @Scheduled(fixedRate = 3000)// 在任务开始后3秒执行下一次调度
//    @Scheduled(fixedDelay = 3000)// 在任务结束后3秒才开始执行
    public void task2(){
        LOGGER.info("==========测试定时任务2开始=========");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("==========测试定时任务2结束=========");
    }
}
