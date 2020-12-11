package workqueue_fair;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {

    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1]" + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                } finally {
                    System.out.println("1 is done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("ack");
                }
            }
        };

        //自动应答 false
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
