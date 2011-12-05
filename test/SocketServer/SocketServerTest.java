package SocketServer;

import SocketServer.Mocks.RequestHandlerFactoryMock;
import SocketServer.Mocks.RequestHandlerMock;
import SocketServer.Utility.Logging;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class SocketServerTest {

    SocketServer socketServer = null;

    @org.junit.Before
    public void setUp() throws Exception {
        Logger logger = Logging.getLoggerAndSetLevel(SocketServerTest.class.getName(), Level.SEVERE);
        socketServer = new SocketServer(8080, new RequestHandlerFactoryMock(), logger);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        socketServer.stopListening();
        RequestHandlerMock.resetCalledCount();
        socketServer = null;
    }

    @org.junit.Test
    public void stopDoesNotThrowAnExceptionIfCalledBeforeStart() throws Exception {
        try {
            socketServer.stopListening();
        } catch (Exception e) {
            fail("Stopping the SocketServer before starting the SocketServer threw an exception");
        }
    }

    @org.junit.Test
    public void serverPortIsTakenAfterStartingTheServer() throws Exception {
        socketServer.startListeningInBackground();
        assertFalse("The port " + socketServer.getPort() + " is available when it should not be.", isPortAvailable(socketServer.getPort()));
    }

    @org.junit.Test
    public void serverPortIsAvailableAfterTheServerIsStopped() throws Exception {
        socketServer.startListeningInBackground();
        socketServer.stopListening();
        assertTrue("The port " + socketServer.getPort() + " is not available when it should be.", isPortAvailable(socketServer.getPort()));
    }

    @org.junit.Test
    public void serverCallsTheDispatcherAfterReceivingARequest() throws Exception {
        socketServer.startListeningInBackground();
        sendRequest(socketServer.getPort());
        socketServer.stopListening();
        assertEquals("The handler was not called.", 1, RequestHandlerMock.getCalledCount());
    }

    @org.junit.Test
    public void serverDoesNotCallTheDispatcherAfterStopping() throws Exception {
        socketServer.startListeningInBackground();
        socketServer.stopListening();
        sendRequest(socketServer.getPort());
        assertEquals("The handler was called.", 0, RequestHandlerMock.getCalledCount());
    }

    private boolean isPortAvailable(int port) {
        ServerSocket ss = null;
        boolean ret = false;
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress("0.0.0.0", port));
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
