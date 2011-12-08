package SocketServer;

import SocketServer.Mocks.RequestDispatcherFactoryMock;
import SocketServer.Mocks.ShutDownHandlerMock;
import SocketServer.Mocks.SocketListenerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class SocketServerTest {

    SocketServer socketServer;
    ShutDownHandlerMock shutDownHandlerMock;
    SocketListenerMock socketListenerMock;
    int port = 8080;
    RequestDispatcherFactory requestDispatcherFactory;


    @Before
    public void setUp() throws Exception {
        this.requestDispatcherFactory = new RequestDispatcherFactoryMock();
        this.shutDownHandlerMock = new ShutDownHandlerMock(null, null);
        this.socketListenerMock = new SocketListenerMock(this.port, new RequestDispatcherFactoryMock(), false);
        this.socketServer = new SocketServer(this.shutDownHandlerMock, this.socketListenerMock);
    }

    @After
    public void tearDown() throws Exception {
        this.requestDispatcherFactory = null;
        this.shutDownHandlerMock = null;
        this.socketListenerMock = null;
        this.socketServer = null;
    }

    @Test
    public void initCorrectly() throws Exception {
        SocketServer socketServer = new SocketServer(this.port, this.requestDispatcherFactory);
        assertEquals(this.port, socketServer.socketListener.socket.port);
        assertEquals(this.requestDispatcherFactory, socketServer.socketListener.requestDispatcherFactory);
        assertEquals(socketServer, socketServer.shutDownHandler.socketServer);
    }

    @Test
    public void addsTheShutdownHandler() throws Exception {
        this.socketServer.start();
        assertEquals(1, this.shutDownHandlerMock.addCalledCount);
    }

    @Test
    public void startsTheSocketListener() throws Exception {
        this.socketServer.start();
        assertEquals(1, this.socketListenerMock.startCalledCount);
    }

    @Test
    public void removesTheShutdownHandler() throws Exception {
        this.socketServer.stop();
        assertEquals(1, this.shutDownHandlerMock.removeCalledCount);
    }

    @Test
    public void removesTheSocketListener() throws Exception {
        this.socketServer.stop();
        assertEquals(1, this.socketListenerMock.stopListeningCalledCount);
    }

    @Test
    public void stopCatchesAnInterruptException() throws Exception {
        this.socketListenerMock.stopListeningThrows = true;
        this.socketServer.stop();
        assertEquals(1, this.shutDownHandlerMock.removeCalledCount);
    }
}
