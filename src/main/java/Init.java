import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/*        */
class Init {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static Connection getConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        final Connection connection = connectionFactory.createConnection ( );
        return connection;
    }
}
