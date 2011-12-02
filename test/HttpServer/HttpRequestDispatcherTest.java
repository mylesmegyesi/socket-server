package HttpServer;

import HttpServer.Mocks.HttpRequestHandlerMock;
import HttpServer.Mocks.HttpRequestParserMock;
import HttpServer.Mocks.SocketMock;
import HttpServer.Utility.Logging;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcherTest {

    HttpRequestDispatcher requestDispatcher = null;
    HttpRequestHandlerMock defaultRequestHandler = null;
    HttpRequestParserMock requestParserMock = null;
    Logger logger;

    @org.junit.Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetHandler(this.getClass().getName(), Level.SEVERE);
        this.defaultRequestHandler = new HttpRequestHandlerMock(true, this.logger);
        this.requestParserMock = new HttpRequestParserMock();
        this.requestDispatcher = new HttpRequestDispatcher(new SocketMock(), this.requestParserMock, new ArrayList<HttpRequestHandler>(), this.defaultRequestHandler, new HttpServerInfo(), this.logger);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        this.defaultRequestHandler.resetHandleCalledCount();
    }

    @org.junit.Test
    public void callsTheRequestParser() throws Exception {
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The request parser was not called.", 1, this.requestParserMock.getCalledCount());
    }

    @org.junit.Test
    public void callsTheDefaultHandlerWithNoHandlersGiven() throws Exception {
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The default handler was not called.", 1, this.defaultRequestHandler.getHandleCalledCount());
    }

    @org.junit.Test
    public void callsTheHandlerGiven() throws Exception {
        HttpRequestHandlerMock requestHandlerMock = new HttpRequestHandlerMock(true, logger);
        this.requestDispatcher.addHandler(requestHandlerMock);
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The given handler was not called.", 1, requestHandlerMock.getHandleCalledCount());
    }

    @org.junit.Test
    public void callsTheSecondHandlerGiven() throws Exception {
        HttpRequestHandlerMock cantHandleRequestHandler = new HttpRequestHandlerMock(false, this.logger);
        HttpRequestHandlerMock canHandleRequestHandler = new HttpRequestHandlerMock(true, this.logger);
        this.requestDispatcher.addHandler(canHandleRequestHandler);
        this.requestDispatcher.addHandler(cantHandleRequestHandler);
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The first handler given was called when it should not have been.", 0, cantHandleRequestHandler.getHandleCalledCount());
        assertEquals("The second handler given was not called.", 1, canHandleRequestHandler.getHandleCalledCount());
    }

}
