package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String QUEUE_NAME = "hello world";
    public static void main(String[] args) throws IOException, TimeoutException {

        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //创建管道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("send: " + message);
        channel.close();
        connection.close();

    }


}
