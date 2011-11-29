package HttpServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import static org.junit.Assert.*;

public class HttpServerTest {

    HttpServer server;

    @org.junit.Before
    public void setUp() throws Exception {
        server = new HttpServer();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        server.stop();
    }

    @org.junit.Test
    public void testConstructorWithDirectory() throws Exception {
        assertEquals(new HttpServer("someDirectory").getDirectory(), "someDirectory");
    }

    @org.junit.Test
    public void testConstructorWithPort() throws Exception {
        assertEquals(new HttpServer(90).getPort(), 90);
    }

    @org.junit.Test
    public void testDirectoryDefault() throws Exception {
        assertEquals(new HttpServer().getDirectory(), ".");
    }

    @org.junit.Test
    public void testPortDefault() throws Exception {
        assertEquals(new HttpServer().getPort(), 8080);
    }

    @org.junit.Test
    public void testThatThePortIsTakenAfterStartingTheServer() throws Exception {
        server.start();
        assertFalse("The port " + server.getPort() + " is available when it should not be.", portAvailable(server.getPort()));
    }

    @org.junit.Test
    public void testThatStopDoesNotThrowAnExceptionIfCalledBeforeStart() throws Exception {
        try {
            server.stop();
        } catch (Exception e) {
            fail("Stopping the server before starting the server threw an exception");
        }
    }

    @org.junit.Test
    public void testThatThePortIsAvailableAfterTheServerIsStopped() throws Exception {
        server.start();
        assertFalse("The port " + server.getPort() + " is available when it should not be.", portAvailable(server.getPort()));
        server.stop();
        assertTrue("The port is still taken.", portAvailable(server.getPort()));
    }

    private boolean portAvailable(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ds = new DatagramSocket(port);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
}
