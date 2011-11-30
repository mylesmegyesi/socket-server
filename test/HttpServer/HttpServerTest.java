package HttpServer;

import HttpServer.Mocks.HttpRequestDispatcherFactoryMock;
import HttpServer.Mocks.HttpRequestDispatcherMock;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class HttpServerTest {

    HttpServer server = null;

    @org.junit.Before
    public void setUp() throws Exception {
        Logger logger = Logger.getLogger("HttpServer");
        logger.setLevel(Level.OFF);
        StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        server = new HttpServer(logger, new HttpRequestDispatcherFactory(), new HttpRequestParserFactory());
    }

    @org.junit.After
    public void tearDown() throws Exception {
        server.stopListening();
        HttpRequestDispatcherMock.resetCalledCount();
        server = null;
    }

    @org.junit.Test
    public void stopDoesNotThrowAnExceptionIfCalledBeforeStart() throws Exception {
        try {
            server.stopListening();
        } catch (Exception e) {
            fail("Stopping the server before starting the server threw an exception");
        }
    }

    @org.junit.Test
    public void serverPortIsTakenAfterStartingTheServer() throws Exception {
        server.start();
        waitForServerToStart(server);
        assertFalse("The port " + server.getPort() + " is available when it should not be.", isPortAvailable(server.getPort()));
    }

    @org.junit.Test
    public void serverPortIsAvailableAfterTheServerIsStopped() throws Exception {
        server.start();
        waitForServerToStart(server);
        server.stopListening();
        assertTrue("The port " + server.getPort() + " is not available when it should be.", isPortAvailable(server.getPort()));
    }

    @org.junit.Test
    public void serverCallsTheDispatcherAfterReceivingARequest() throws Exception {
        server.start();
        waitForServerToStart(server);
        server.setDispatcherFactory(new HttpRequestDispatcherFactoryMock());
        sendRequest(server.getPort());
        server.stopListening();
        assertEquals("The dispatcher was not called.", 1, HttpRequestDispatcherMock.getCalledCount());
    }

    @org.junit.Test
    public void serverDoesNotCallTheDispatcherAfterStopping() throws Exception {
        server.start();
        waitForServerToStart(server);
        server.setDispatcherFactory(new HttpRequestDispatcherFactoryMock());
        server.stopListening();
        sendRequest(server.getPort());
        assertEquals("The dispatcher was called.", 0, HttpRequestDispatcherMock.getCalledCount());
    }

    private void waitForServerToStart(HttpServer server) {
        while (!server.serverIsListening()) {
        }
    }

    private boolean isPortAvailable(int port) {
        ServerSocket ss = null;
        boolean ret = false;
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress("localhost", port));
            ret = ss.isBound();
        } catch (IOException e) {
        } finally {
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException e) {
            }
        }
        return ret;
    }

    private void sendRequest(int port) {
        Socket socket = null;
        OutputStream out = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", port), 100);
            out = socket.getOutputStream();
            out.write("message".getBytes());
        } catch (IOException e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
