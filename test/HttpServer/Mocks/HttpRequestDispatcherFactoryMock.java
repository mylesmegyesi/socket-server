package HttpServer.Mocks;

import HttpServer.*;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcherFactoryMock extends HttpRequestDispatcherFactory {

    @Override
    public HttpRequestDispatcher create(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, HttpServerInfo serverInfo, Logger logger) {
        return new HttpRequestDispatcherMock(socket, requestParser, requestHandlers, defaultHandler, serverInfo, logger);
    }

}
