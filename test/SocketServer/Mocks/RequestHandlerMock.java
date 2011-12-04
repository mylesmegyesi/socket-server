package SocketServer.Mocks;

import SocketServer.RequestHandler;
import SocketServer.RequestParser;
import SocketServer.Responder;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerMock extends RequestHandler {

    public RequestHandlerMock(Socket socket, RequestParser requestParser, List<Responder> responders, Logger logger) {
        super(socket, requestParser, responders, logger);
    }

    public static int getCalledCount() {
        return calledCount;
    }

    public static void resetCalledCount() {
        RequestHandlerMock.calledCount = 0;
    }

    private static int calledCount = 0;

    @Override
    public void run() {
        calledCount++;
    }
}
