import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.junit.Before.*;
import static org.junit.BeforeClass.*;
import static org.junit.Test.*;


public class ClientServerTest {

    @Before
    public void startServerTest() {
        // Start server before running this test
    }

    @Test
    public void testPostClient() {
        Client client = new Client();
        String testGet = client.testGet("localhost","8080","GET","name");
        assertEquals("null",testGet);
        boolean post = client.testPost("localhost","8080","POST","name","Gideon");
        testGet = client.testGet("localhost","8080","GET","name");
        assertEquals(true, post);
        assertEquals("Gideon",testGet);
    }

    @Test
    public void testPostClients() {
        Client client1 = new Client();
        Client client2 = new Client();
        boolean post = client1.testPost("localhost","8080","POST","name","Gideon");
        String clientGet = client1.testGet("localhost","8080","GET","name");
        String client2Get = client2.testGet("localhost","8080","GET","name");
        assertEquals(true,post);
        assertEquals("Gideon",clientGet);
        assertEquals("Gideon",client2Get);
    }

    public void testFailure() {
        fail("This is a check to make sure failures reported.");
    }

}
