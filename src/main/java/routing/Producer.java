package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg1 = "hello direct";
        String routingKey1 = "hello";
        channel.basicPublish(EXCHANGE_NAME, routingKey1, null, msg1.getBytes());

        String msg2 = "fuck";
        String routingKey2 = "fuck";
        channel.basicPublish(EXCHANGE_NAME, routingKey2, null, msg2.getBytes());


        channel.close();
        connection.close();
    }
}
