package HttpServer;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class HttpRequestDispatcherFactory {

    public HttpRequestDispatcher create(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, Logger logger) {
        return new HttpRequestDispatcher(socket, requestParser, requestHandlers, defaultHandler, logger);
    }

}
