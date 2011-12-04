package SocketServer;

import SocketServer.Mocks.RequestHandlerMock;
import SocketServer.Mocks.RequestParserMock;
import SocketServer.Mocks.ResponderMock;
import SocketServer.Mocks.SocketMock;
import SocketServer.Utility.Logging;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerTest {

    RequestHandler requestHandler = null;
    RequestParserMock requestParserMock = null;
    Logger logger;

    @org.junit.Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetLevel(this.getClass().getName(), Level.SEVERE);
        this.requestParserMock = new RequestParserMock();
        this.requestHandler = new RequestHandler(new SocketMock(), this.requestParserMock, new ArrayList<Responder>(), this.logger);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        //this.defaultRequestHandler.resetHandleCalledCount();
    }

    @org.junit.Test
    public void callsTheRequestParser() throws Exception {
        Thread thread = new Thread(requestHandler);
        thread.start();
        thread.join();
        assertEquals("The request parser was not called.", 1, this.requestParserMock.getCalledCount());
    }

    @org.junit.Test
    public void callsTheHandlerGiven() throws Exception {
        ResponderMock requestHandlerMock = new ResponderMock(true);
        this.requestHandler.addHandler(requestHandlerMock);
        Thread thread = new Thread(requestHandler);
        thread.start();
        thread.join();
        assertEquals("The given handler was not called.", 1, requestHandlerMock.getCalledCount());
    }

    @org.junit.Test
    public void callsTheSecondHandlerGiven() throws Exception {
        ResponderMock cantHandleRequestHandler = new ResponderMock(false);
        ResponderMock canHandleRequestHandler = new ResponderMock(true);
        this.requestHandler.addHandler(canHandleRequestHandler);
        this.requestHandler.addHandler(cantHandleRequestHandler);
        Thread thread = new Thread(requestHandler);
        thread.start();
        thread.join();
        assertEquals("The first handler given was called when it should not have been.", 0, cantHandleRequestHandler.getCalledCount());
        assertEquals("The second handler given was not called.", 1, canHandleRequestHandler.getCalledCount());
    }

}
