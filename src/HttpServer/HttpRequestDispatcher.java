package HttpServer;


import HttpServer.Exceptions.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestDispatcher implements Runnable {

    protected HttpRequestDispatcher() {

    }

    public HttpRequestDispatcher(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.requestHandlers = requestHandlers;
        this.defaultHandler = defaultHandler;
        this.logger = logger;
    }

    public void addHandler(HttpRequestHandler requestHandler) {
        this.requestHandlers.add(requestHandler);
    }

    public void run() {
        InputStream inputStream;
        try {
            inputStream = this.socket.getInputStream();
        } catch (IOException e) {
            this.logger.severe("Could not open the input stream of the socket.");
            return;
        }
        HttpRequest request = null;
        try {
            request = this.requestParser.parse(inputStream);
        } catch (IOException e) {
            defaultHandler.handle(request);
            return;
        } catch (BadRequestException e) {
            defaultHandler.handle(request);
            return;
        }
        boolean handled = false;
        for (HttpRequestHandler requestHandler : this.requestHandlers) {
            if (requestHandler.canHandle(request)) {
                requestHandler.handle(request);
                handled = true;
            }
        }
        if (!handled) {
            defaultHandler.handle(request);
        }
    }

    private Socket socket;
    private HttpRequestParser requestParser;
    private List<HttpRequestHandler> requestHandlers;
    private HttpRequestHandler defaultHandler;
    private Logger logger;
}
