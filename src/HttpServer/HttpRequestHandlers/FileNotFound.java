package HttpServer.HttpRequestHandlers;

import HttpServer.HttpRequest;
import HttpServer.HttpRequestHandler;


public class FileNotFound implements HttpRequestHandler {
    public boolean canHandle(HttpRequest request) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handle(HttpRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
