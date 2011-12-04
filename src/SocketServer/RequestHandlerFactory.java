package SocketServer;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class RequestHandlerFactory {

    public RequestHandlerFactory(RequestParser requestParser, List<Responder> responders, Logger logger) {
        this.requestParser = requestParser;
        this.responders = responders;
        this.logger = logger;
    }

    public RequestHandler create(Socket socket) {
        return new RequestHandler(socket, this.requestParser, this.responders, this.logger);
    }

    protected RequestParser requestParser;
    protected List<Responder> responders;
    protected Responder defaultHandler;
    protected Logger logger;

}
