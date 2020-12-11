package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String QUEUE_NAME = "workQueue";

    public static void main(String[] args) {

        Channel channel = null;
        Connection connection = null;

        try {
            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 20; i++) {
                String msg = "|" + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("utf-8"));
                Thread.sleep(i * 10);
            }
            channel.close();
            connection.close();
        }catch (Exception e) {
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (Exception e) {
            }
        }
    }
}
