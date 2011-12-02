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

    public HttpRequestDispatcher(Socket socket, HttpRequestParser requestParser, List<HttpRequestHandler> requestHandlers, HttpRequestHandler defaultHandler, HttpServerInfo serverInfo, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.requestHandlers = requestHandlers;
        this.defaultHandler = defaultHandler;
        this.logger = logger;
        this.serverInfo = serverInfo;
    }

    public void addHandler(HttpRequestHandler requestHandler) {
        this.requestHandlers.add(requestHandler);
    }

    public void run() {
        this.logger.info(String.format("Received request from %s:%d", this.socket.getInetAddress(), this.socket.getLocalPort()));
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
        } catch (BadRequestException e) {
            this.logger.severe(String.format("Unable to parse request. %s %s", "\n", e.getMessage()));
            defaultHandler.handle(socket, request, this.serverInfo);
            return;
        }
        boolean handled = false;
        for (HttpRequestHandler requestHandler : this.requestHandlers) {
            if (requestHandler.canHandle(request)) {
                this.logger.info(String.format("Request is being handled by %s", requestHandler.getClass()));
                requestHandler.handle(socket, request, this.serverInfo);
                handled = true;
            }
        }
        if (!handled) {
            this.logger.info("Suitable handler not found, calling default handler.");
            defaultHandler.handle(socket, request, this.serverInfo);
        }
    }

    private Socket socket;
    private HttpRequestParser requestParser;
    private List<HttpRequestHandler> requestHandlers;
    private HttpRequestHandler defaultHandler;
    private HttpServerInfo serverInfo;
    private Logger logger;
}
