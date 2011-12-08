package SocketServer;

import SocketServer.Mocks.DispatcherPoolMock;
import SocketServer.Mocks.RequestDispatcherFactoryMock;
import SocketServer.Mocks.SocketMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class SocketListenerTest {

    SocketListener socketListener;
    SocketMock socketMock;
    RequestDispatcherFactory requestDispatcherFactory;
    DispatcherPoolMock dispatcherPoolMock;

    @Before
    public void setUp() throws Exception {
        this.socketMock = new SocketMock(0, false, false);
        this.requestDispatcherFactory = new RequestDispatcherFactoryMock();
        this.dispatcherPoolMock = new DispatcherPoolMock(null);
        this.socketListener = new SocketListener(this.socketMock, this.requestDispatcherFactory, this.dispatcherPoolMock);
    }

    @After
    public void tearDown() throws Exception {
        this.socketListener = null;
    }

    @Test
    public void startingOpensTheSocket() throws Exception {
        startAndStop();
        assertTrue(this.socketMock.openCalledCount > 0);
    }

    @Test
    public void acceptChecksTheBinding() throws Exception {
        startAndStop();
        assertTrue(this.socketMock.isBoundCalledCount > 0);
    }

    @Test
    public void acceptStopsListeningAfterClosingSocket() throws Exception {
        startAndStop();
        assertTrue(this.socketMock.acceptCalledCount > 0);
    }

    @Test
    public void acceptStopsListeningAfterSocketException() throws Exception {
        this.socketMock.acceptThrows = true;
        startAndStop();
        assertTrue(this.socketMock.acceptCalledCount > 0);
    }

    @Test
    public void stopClosesTheSocket() throws Exception {
        startAndStop();
        assertTrue(this.socketMock.closeCalledCount > 0);
    }

    @Test
    public void closeCatchesIOException() throws Exception {
        this.socketMock.closeThrows = true;
        startAndStop();
        assertTrue(this.socketMock.closeCalledCount > 0);
    }

    private void startAndStop() throws InterruptedException {
        this.socketListener.start();
        Thread.sleep(1);
        this.socketListener.stopListening();
    }
}
