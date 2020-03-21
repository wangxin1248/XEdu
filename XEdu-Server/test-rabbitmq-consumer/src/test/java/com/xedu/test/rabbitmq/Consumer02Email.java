package com.xedu.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/21 19:56.
 * @Description: 发布订阅模型测试-订阅email
 */
public class Consumer02Email {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
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
        // 建立连接
        Connection connection = connectionFactory.newConnection();
        // 建立channel
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
        channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

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
        // 创建消费者方法
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * 消费者接收消息时调用此方法
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
             * @param properties 消息属性
             * @param body 消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 交换机
                String exchange = envelope.getExchange();
                // 路由key
                String routingKey = envelope.getRoutingKey();
                // 消息id
                long deliveryTag = envelope.getDeliveryTag();
                // 消息内容
                String msg = new String(body,"utf-8");
                System.out.println("receive message.." + msg);
            }
        };
        // 监听队列
        /**
         * 参数
         * 1。queue：队列名称
         * 2。autoACK：自动回复，当消费者接受到消息后要告诉
         * 3。消费消息的方法，消费者接收到消息后调用此方法
         */
        channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
    }
}
