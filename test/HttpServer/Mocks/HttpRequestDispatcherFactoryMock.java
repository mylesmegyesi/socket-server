package HttpServer.Mocks;

import HttpServer.HttpRequestDispatcher;
import HttpServer.HttpRequestDispatcherFactory;
import HttpServer.HttpRequestHandler;
import HttpServer.HttpRequestParser;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: mylesmegyesi
 * Date: 11/30/11
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestDispatcherFactoryMock extends HttpRequestDispatcherFactory {

    @Override
    public HttpRequestDispatcher create(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, Logger logger) {
        return new HttpRequestDispatcherMock(socket, requestParser, requestHandlers, defaultHandler, logger);
    }

}
