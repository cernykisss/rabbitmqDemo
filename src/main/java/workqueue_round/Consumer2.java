package workqueue_round;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    private static final String QUEUE_NAME = "workQueue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");
                System.out.println("[2] " + msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        };
        System.out.println("Consumer2 is done");
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
