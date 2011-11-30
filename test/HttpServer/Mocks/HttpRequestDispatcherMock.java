package HttpServer.Mocks;

import HttpServer.HttpRequestDispatcher;

import java.net.Socket;
import java.util.logging.Logger;

public class HttpRequestDispatcherMock extends HttpRequestDispatcher {

    public static int getCalledCount() {
        return calledCount;
    }

    public static void resetCalledCount() {
        HttpRequestDispatcherMock.calledCount = 0;
    }

    private static int calledCount = 0;

    public HttpRequestDispatcherMock(Socket socket, Logger logger) {

    }

    @Override
    public void run() {
        calledCount++;
    }
}
