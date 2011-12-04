package SocketServer.Mocks;

import SocketServer.RequestHandler;
import SocketServer.RequestHandlerFactory;
import SocketServer.RequestParser;
import SocketServer.Responder;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerFactoryMock extends RequestHandlerFactory {

    public RequestHandlerFactoryMock(Logger logger) {
        super(new RequestParserMock(), new ArrayList<Responder>(), logger);
    }


    public RequestHandlerFactoryMock(RequestParser requestParser, List<Responder> responders, Logger logger) {
        super(requestParser, responders, logger);
    }

    public RequestHandler create(Socket socket) {
        return new RequestHandlerMock(socket, this.requestParser, this.responders, this.logger);
    }
}
