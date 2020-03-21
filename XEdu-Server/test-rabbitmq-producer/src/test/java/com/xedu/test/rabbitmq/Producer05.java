package com.xedu.test.rabbitmq;

import com.xedu.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/21 17:11.
 * @Description: 测试使用spring boot 的 RabbitTemplate 来发送消息
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05 {
    @Autowired
    RabbitTemplate rabbitTemplate;
    // 使用rabbitTemplate来发送消息
    @Test
    public void testSendEmail(){
        String message = "send email to user";
        /**
         * 参数
         * 1。交换机名称
         * 2。routingKey
         * 3。消息内容
         */
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
    }
}
