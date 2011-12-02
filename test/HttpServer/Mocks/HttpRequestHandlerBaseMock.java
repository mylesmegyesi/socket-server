package HttpServer.Mocks;

import HttpServer.HttpRequest;
import HttpServer.HttpRequestHandler;
import HttpServer.HttpResponse;
import HttpServer.HttpServerInfo;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHandlerBaseMock extends HttpRequestHandler {

    public HttpRequestHandlerBaseMock(Logger logger) {
        super(logger);
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handle(Socket socket, HttpRequest request, HttpServerInfo serverInfo) {
        this.getResponse(request, new HttpServerInfo());
    }
}
