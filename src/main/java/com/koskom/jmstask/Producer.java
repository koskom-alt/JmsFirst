package com.koskom.jmstask;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

class Producer {
    private String queues;
    private Connection connection;
    private MessageProducer messageProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    Producer(String queues, Connection connection){
        this.queues = queues;
        this.connection = connection;
    }



    public void create() throws JMSException {
        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue (queues);
        messageProducer = session.createProducer(queue);
    }

    public void close() throws JMSException {
        connection.close();
    }


    public void sendMessage (Message message) throws JMSException{
        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            if (!textMessage.getText ().isEmpty ()){
                LOGGER.debug("producer sent message with text='{}'", textMessage.getText ());
                messageProducer.send (message);
            }else{
                LOGGER.debug ("message is Empty");
            }
        }
    }
}