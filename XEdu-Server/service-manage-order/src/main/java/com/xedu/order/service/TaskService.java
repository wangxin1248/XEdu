package com.xedu.order.service;

import com.xedu.framework.domain.task.XcTask;
import com.xedu.framework.domain.task.XcTaskHis;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.order.dao.XcTaskHisRepository;
import com.xedu.order.dao.XcTaskRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 15:32.
 * @Description:
 */
@Service
public class TaskService {
    /**
     * 导入dao
     */
    @Autowired
    XcTaskRepository xcTaskRepository;
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;
    /**
     * 导入rabbitmq的操作template
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 查询前n条记录
     * @param updateTime
     * @param size
     * @return
     */
    public List<XcTask> findXcTaskList(Date updateTime,int size){
        // 设置分页参数
        Pageable pageable = new PageRequest(0,size);
        // 查询前n条任务
        Page<XcTask> all = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        List<XcTask> content = all.getContent();
        return content;
    }

    /**
     * 执行任务处理消息发布
     * @param xcTask 所要处理的任务
     * @param ex 交换机名称
     * @param routingKey 路由key
     */
    @Transactional(rollbackFor = {})
    public void publish(XcTask xcTask, String ex, String routingKey){
        // 首先从事数据库中查询所传递的任务是否存在数据库中
        Optional<XcTask> optional = xcTaskRepository.findById(xcTask.getId());
        if(optional.isPresent()){
            // 调用rabbitmq发送消息
            rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
            // 更新任务的更新时间
            XcTask one = optional.get();
            one.setUpdateTime(new Date());
            xcTaskRepository.save(one);
        }
    }

    /**
     * 获取任务去执行
     * @param id
     * @param version
     * @return
     */
    @Transactional(rollbackFor = {})
    public int getTask(String id,int version){
        // 通过乐观锁的方式来更新数据表，如果结果大于0说明取到任务
        int count = xcTaskRepository.updateTaskVersion(id, version);
        return count;
    }


    /**
     * 完成任务
     * @param taskId
     */
    @Transactional(rollbackFor = {})
    public void finishTask(String taskId){
        if(StringUtils.isEmpty(taskId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<XcTask> optionalXcTask = xcTaskRepository.findById(taskId);
        if(optionalXcTask.isPresent()){
            // 获取当前任务
            XcTask xcTask = optionalXcTask.get();
            // 创建历史任务对象
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            // 保存历史任务对象
            xcTaskHisRepository.save(xcTaskHis);
            // 删除当前任务
            xcTaskRepository.delete(xcTask);
        }
    }
}
