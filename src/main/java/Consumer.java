import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;

class Consumer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final Logger LOGGER =
            LoggerFactory.getLogger (Consumer.class);
    private MessageConsumer messageConsumer;


    public void create(Connection connection) throws JMSException {

        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue ("testQueue");
        messageConsumer = session.createConsumer (destination);
    }

    public void setMessageListener(MessageListener lister) throws JMSException {
        messageConsumer.setMessageListener (lister);
    }
}



