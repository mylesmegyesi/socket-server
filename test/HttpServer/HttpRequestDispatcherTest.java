package HttpServer;

import HttpServer.Mocks.HttpRequestHandlerMock;
import HttpServer.Mocks.HttpRequestParserMock;
import HttpServer.Mocks.SocketMock;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcherTest {

    HttpRequestDispatcher requestDispatcher = null;
    HttpRequestHandlerMock defaultRequestHandler = null;
    HttpRequestParserMock requestParserMock = null;

    @org.junit.Before
    public void setUp() throws Exception {
        Logger logger = Logger.getLogger("HttpServer");
        logger.setLevel(Level.OFF);
        StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        defaultRequestHandler = new HttpRequestHandlerMock(true);
        requestParserMock = new HttpRequestParserMock();
        requestDispatcher = new HttpRequestDispatcher(new SocketMock(), requestParserMock, new ArrayList<HttpRequestHandler>(), defaultRequestHandler, logger);
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
        HttpRequestHandlerMock requestHandlerMock = new HttpRequestHandlerMock(true);
        this.requestDispatcher.addHandler(requestHandlerMock);
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The given handler was not called.", 1, requestHandlerMock.getHandleCalledCount());
    }

    @org.junit.Test
    public void callsTheSecondHandlerGiven() throws Exception {
        HttpRequestHandlerMock cantHandleRequestHandler = new HttpRequestHandlerMock(false);
        HttpRequestHandlerMock canHandleRequestHandler = new HttpRequestHandlerMock(true);
        this.requestDispatcher.addHandler(canHandleRequestHandler);
        this.requestDispatcher.addHandler(cantHandleRequestHandler);
        Thread thread = new Thread(requestDispatcher);
        thread.start();
        thread.join();
        assertEquals("The first handler given was called when it should not have been.", 0, cantHandleRequestHandler.getHandleCalledCount());
        assertEquals("The second handler given was not called.", 1, canHandleRequestHandler.getHandleCalledCount());
    }

}
