package workqueue_fair;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //持久化
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2]" + msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                } finally {
                    System.out.println("2 is done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("ack");
                }
            }
        };

        //自动确认模式 false
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
