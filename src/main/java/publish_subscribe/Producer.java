package publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String msg = "hello exchange";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("utf-8"));
        System.out.println("send " + msg);
        channel.close();
        connection.close();

    }
}
