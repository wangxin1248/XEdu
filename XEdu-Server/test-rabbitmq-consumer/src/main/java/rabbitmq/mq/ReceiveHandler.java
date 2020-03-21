package rabbitmq.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.config.RabbitmqConfig;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/21 22:01.
 * @Description: 使用springboot接受mq的信息
 */
@Component
public class ReceiveHandler {
    // 监听email队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receiveEmail(String msg, Message message, Channel channel){
        System.out.println(msg);
    }
    // 监听sms队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void receiveSms(String msg, Message message, Channel channel){
        System.out.println(msg);
    }
}
