package HttpServer.Mocks;

import HttpServer.HttpRequestDispatcher;
import HttpServer.HttpRequestHandler;
import HttpServer.HttpRequestParser;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcherMock extends HttpRequestDispatcher {

    public static int getCalledCount() {
        return calledCount;
    }

    public static void resetCalledCount() {
        HttpRequestDispatcherMock.calledCount = 0;
    }

    private static int calledCount = 0;

    public HttpRequestDispatcherMock(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, Logger logger) {

    }

    @Override
    public void run() {
        calledCount++;
    }
}
