package HttpServer;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class HttpRequestDispatcherFactory {

    public HttpRequestDispatcher create(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, HttpServerInfo serverInfo, Logger logger) {
        return new HttpRequestDispatcher(socket, requestParser, requestHandlers, defaultHandler, serverInfo, logger);
    }

}
