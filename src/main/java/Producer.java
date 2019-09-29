import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

class Producer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Producer.class);

    private MessageProducer messageProducer;

    public void create(Connection connection) throws JMSException {
        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue ("testQueue2,testQueue3");
        messageProducer = session.createProducer(queue);
    }

    public void close(Connection connection) throws JMSException {
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