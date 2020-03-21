package com.xedu.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/21 12:28.
 * @Description: rabbitmq 入门程序
 */
public class Producer01 {
    private static final String QUEUE = "HelloWorld";
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
        /**
         * 参数
         * 1。queue：队列名称
         * 2。durable：是否持久化，mq重启后队列还在
         * 3。exclusive：是否独占连接，队列只允许在该连接中访问，如果连接关闭队列自动删除，如果将此参数设置为true，可用于临时队列的创建
         * 4。autoDelete：自动删除队列，队列不使用时自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了自动删除）
         * 5。Map<String,Object> arguments：设置队列的扩展参数
         */
        channel.queueDeclare(QUEUE,true,false,false,null);
        // 所要发送的消息内容
        String message = "hello eorld wang xin";
        // 发送消息
        /**
         * 参数
         * 1。exchange：交换机，如果不指定将使用mq的默认交换机（设置为""）
         * 2。routingKey：路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
         * 3。props：消息的属性
         * 4。body：消息内容
         */
        channel.basicPublish("",QUEUE,null,message.getBytes());
        System.out.println("send to mq"+message);

        // 关闭流
        channel.close();
        connection.close();
    }
}
