package com.koskom.jmstask;


import org.apache.activemq.ActiveMQConnection;

import javax.jms.*;
import java.util.HashSet;
import java.util.Set;

class MessageDuplicator {

    private Consumer consumer;
    private Producer producer;
    private String consumerQueue;
    private String url;
    private String producerQueues;
    private Connection connection;

    MessageDuplicator (String url, String inQueue, Set<String> outQueues) {

        this.url = url;
        this.consumerQueue = inQueue;
        this.producerQueues = String.join(",", outQueues);

    }
    public void stop(){
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public void run() throws JMSException {


        connection = InitConnection.getConnection(url);

        producer = new Producer(producerQueues, connection);
        consumer = new Consumer(consumerQueue, connection);

        consumer.create();
        producer.create();

        MessageListener lister = message -> {
            try {
                producer.sendMessage(message);
            } catch (JMSException e) {
                System.out.println("Caught:" + e);
                e.printStackTrace();
            }
        };
        consumer.setMessageListener(lister);
        connection.start();
    }

    public static void main(String[] args) throws JMSException {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        Set<String> outgoingQueues = new HashSet();
        outgoingQueues.add("testQueue2");
        outgoingQueues.add("testQueue3");
        String inQueue = "testQueue";

        MessageDuplicator messageDuplicator = new MessageDuplicator(url, inQueue, outgoingQueues);
        messageDuplicator.run();
    }
}
