package HttpServer.Mocks;

import HttpServer.HttpRequestDispatcher;
import HttpServer.HttpRequestDispatcherFactory;
import HttpServer.HttpRequestHandler;
import HttpServer.HttpRequestParser;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcherFactoryMock extends HttpRequestDispatcherFactory {

    @Override
    public HttpRequestDispatcher create(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, Logger logger) {
        return new HttpRequestDispatcherMock(socket, requestParser, requestHandlers, defaultHandler, logger);
    }

}
