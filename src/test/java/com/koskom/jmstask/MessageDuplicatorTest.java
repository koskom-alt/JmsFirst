package com.koskom.jmstask;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.*;

import javax.jms.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class MessageDuplicatorTest {
    public String url;
    public Set<String> outgoingQueues;
    public String inQueue;
    public TextMessage mess;
    public MessageProducer messageProducer;
    public MessageDuplicator messageDuplicator;
    public Session session;

    @BeforeEach
    public void prepareTestBroker() throws JMSException {
        url = "vm://localhost?broker.persistent=false";
        outgoingQueues = new HashSet();
        outgoingQueues.add("testQueue2");
        outgoingQueues.add("testQueue3");
        inQueue = "testQueue";
        messageDuplicator = new MessageDuplicator(url, inQueue, outgoingQueues);
        try {
            messageDuplicator.run();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        final javax.jms.Connection connection = connectionFactory.createConnection ( );
        session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

        connection.start();

        Queue destQueue = new ActiveMQQueue (inQueue);
        messageProducer = session.createProducer(destQueue);
        mess = session.createTextMessage("testMessage");
        messageProducer.send(mess);

    }


    @Test
    void checkCountMessagesInOutgoingQueues() throws JMSException, InterruptedException {
        Thread.sleep(3000);
        for (String queueName : outgoingQueues) {
            Queue queue = new ActiveMQQueue(queueName);
            QueueBrowser browser = session.createBrowser(queue);
            int count = Collections.list(browser.getEnumeration()).size();

            Assertions.assertEquals(1, count, "countIsInCorrect");
        }
    }

    @Test
    void checkMessagesBodyInOutgoingQueues() throws JMSException {

        for (String queueName : outgoingQueues) {
            Destination destination = session.createQueue (queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            TextMessage message = (TextMessage) consumer.receive();

            Assertions.assertNotNull(message);
            Assertions.assertSame("testMessage", message.getText());
        }
    }

    @AfterEach
    public void stopDuplicator(){
        messageDuplicator.stop();
    }

}