package com.koskom.jmstask;

import javax.jms.*;

class Consumer {

    private String queue;
    private Connection connection;
    private MessageConsumer messageConsumer;

    Consumer(String queue, Connection connection) {
        this.queue = queue;
        this.connection = connection;
    }

    public void create() throws JMSException {

        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue (queue);
        messageConsumer = session.createConsumer (destination);
    }

    public void setMessageListener(MessageListener lister) throws JMSException {
        messageConsumer.setMessageListener (lister);
    }
}



