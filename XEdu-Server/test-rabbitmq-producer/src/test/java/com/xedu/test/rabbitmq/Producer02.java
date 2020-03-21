package com.xedu.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/21 19:14.
 * @Description: 发布订阅模型测试
 */
public class Producer02 {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM="exchange_fanout_inform";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 通过连接工厂创建新的连接对象和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接工厂的相关参数
        connectionFactory.setHost("192.168.116.129");// ip地址
        connectionFactory.setPort(5672);// 端口
        connectionFactory.setUsername("admin");// 用户名
        connectionFactory.setPassword("admin");// 密码
        // 设置虚拟机，一个mq的服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

        // 建立新连接
        Connection connection = connectionFactory.newConnection();
        // 创建会话channel，生产者和mq服务所有的通信都在channel中完成
        Channel channel = connection.createChannel();

        // 声明队列，如果队列在mq中没有则创建
        channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
        channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

        // 声明交换机
        /** 参数
         * 1。交换机名称
         * 2。交换机类型
         * fanout：发布订阅模式
         * topic：通配符模式
         * direct：路由模式
         * headers：headers模式
         */
        channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);

        // 将路由与交换机进行绑定
        /** 参数明细
         * 1。队列名称
         * 2。交换机名称
         * 3。路由key
         */
        channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
        channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");

        // 开始发送消息
        String message = "send a message";
        for(int i=0;i<5;i++){
            channel.basicPublish(EXCHANGE_FANOUT_INFORM,"",null,message.getBytes());
            System.out.println(message);
        }
        channel.close();
        connection.close();
    }
}
