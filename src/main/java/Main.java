import javax.jms.*;

public class Main {
    public static void main(String[] args) throws JMSException {
        Connection connection = Init.getConnection ( );

        Consumer consumer = new Consumer ();
        consumer.create (connection);


        final Producer producer = new Producer ();
        producer.create (connection);

        connection.start ();

        MessageListener lister = new MessageListener ( ) {
            public void onMessage(Message message) {
                try {
                    producer.sendMessage (message);
                } catch (JMSException e) {
                    System.out.println ("Caught:" + e);
                    e.printStackTrace ( );
                }
            }
        };
        consumer.setMessageListener (lister);
    }
}
