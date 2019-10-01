package com.koskom.jmstask;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

class InitConnection {

    public static javax.jms.Connection getConnection(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        final javax.jms.Connection connection = connectionFactory.createConnection ( );
        return connection;
    }
}
