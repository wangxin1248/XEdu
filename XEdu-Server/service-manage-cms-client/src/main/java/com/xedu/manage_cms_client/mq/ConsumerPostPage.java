package com.xedu.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.manage_cms_client.dao.CmsPageRepository;
import com.xedu.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 12:30.
 * @Description:
 */
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);

    // 注入所需的组件
    @Autowired
    PageService pageService;
    @Autowired
    CmsPageRepository cmsPageRepository;

    // rabbitmq监听对应的queue
    @RabbitListener(queues = "${xedu.mq.queue}")
    public void postpage(String msg){
        // 解析消息
        Map map = JSON.parseObject(msg, Map.class);
        // 取出页面id
        String pageId = (String) map.get("pageId");
        // 判断页面id是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(!optional.isPresent()){
            LOGGER.error("receive cms post page,cmsPage is null:{}",msg.toString());
            return;
        }
        // 执行service中的方法，从gridFs中将文件保存到服务器本地
        pageService.savePage2Serverpath(pageId);
    }
}
