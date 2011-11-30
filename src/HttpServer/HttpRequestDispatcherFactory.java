package HttpServer;

import java.net.Socket;
import java.util.logging.Logger;

public class HttpRequestDispatcherFactory {

    public HttpRequestDispatcher create(Socket socket, Logger logger) {
        return new HttpRequestDispatcher(socket, logger);
    }

}
