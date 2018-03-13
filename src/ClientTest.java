import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClientTest {
    @Before
    public void startServerTest() {
        // Run ClientServerTest first and then restart server manually
        // Note: I couldn't figure out running Server and Client on JUnit Test.
    }

    @Test
    public void testPostClient() {
        Client client = new Client();
        String testGet = client.testGet("localhost","8080","GET","name");
        assertEquals("Gideon",testGet);
    }

    @Test
    public void testPostClients() {
        Client client1 = new Client();
        Client client2 = new Client();
        String clientGet = client1.testGet("localhost","8080","GET","name");
        String client2Get = client2.testGet("localhost","8080","GET","name");
        assertEquals("Gideon",clientGet);
        assertEquals("Gideon",client2Get);
    }

    public void testFailure() {
        fail("This is a check to make sure failures reported.");
    }
}
