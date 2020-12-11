package workqueue_fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        //消费者返回确认消息之前只有一个消息发给消费者
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i < 30; i++) {
            String msg = "|" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            try {
                Thread.sleep(i * 5);
            } catch (InterruptedException e) {
            }
        }

        channel.close();
        connection.close();


    }


}
