import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.Connection;
import javax.jms.JMSException;

import static org.junit.jupiter.api.Assertions.*;

class InitTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getConnection() throws JMSException {
        Connection connection = Init.getConnection ();

    }
}