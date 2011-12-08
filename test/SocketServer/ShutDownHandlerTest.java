package SocketServer;

import SocketServer.Mocks.RuntimeMock;
import SocketServer.Mocks.SocketServerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Author: Myles Megyesi
 */
public class ShutDownHandlerTest {

    ShutDownHandler shutDownHandler;
    RuntimeMock runtimeMock;
    SocketServerMock socketServerMock;

    @Before
    public void setUp() throws Exception {
        this.runtimeMock = new RuntimeMock(Runtime.getRuntime(), false, false, false);
        this.socketServerMock = new SocketServerMock();
        this.shutDownHandler = new ShutDownHandler(this.socketServerMock, this.runtimeMock);
    }

    @After
    public void tearDown() throws Exception {
        this.runtimeMock = null;
        this.socketServerMock = null;
        this.shutDownHandler = null;
    }

    @Test
    public void runCallsStop() throws Exception {
        this.shutDownHandler.run();
        assertEquals(1, this.socketServerMock.stopCalledCount);
    }

    @Test
    public void runDoesNotThrowWhenServerIsNull() throws Exception {
        this.shutDownHandler.socketServer = null;
        try {
            this.shutDownHandler.run();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addCallsRuntime() throws Exception {
        this.shutDownHandler.addShutDownHook();
        assertEquals(1, this.runtimeMock.addCalledCount);
    }

    @Test
    public void removeCallsRuntime() throws Exception {
        this.shutDownHandler.removeShutDownHook();
        assertEquals(1, this.runtimeMock.removeCalledCount);
    }

    @Test
    public void addDoesNotThrowIllegalState() throws Exception {
        this.runtimeMock = new RuntimeMock(Runtime.getRuntime(), true, false, false);
        this.socketServerMock = new SocketServerMock();
        this.shutDownHandler = new ShutDownHandler(this.socketServerMock, this.runtimeMock);
        try {
            this.shutDownHandler.addShutDownHook();
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void addHandlerDoesNotThrowIllegalArg() throws Exception {
        this.runtimeMock = new RuntimeMock(Runtime.getRuntime(), false, true, false);
        this.socketServerMock = new SocketServerMock();
        this.shutDownHandler = new ShutDownHandler(this.socketServerMock, this.runtimeMock);
        try {
            this.shutDownHandler.addShutDownHook();
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void removeDoesNotThrowIllegalState() throws Exception {
        this.runtimeMock = new RuntimeMock(Runtime.getRuntime(), false, false, true);
        this.socketServerMock = new SocketServerMock();
        this.shutDownHandler = new ShutDownHandler(this.socketServerMock, this.runtimeMock);
        try {
            this.shutDownHandler.removeShutDownHook();
        } catch (IllegalStateException e) {
            fail();
        }
    }
}
