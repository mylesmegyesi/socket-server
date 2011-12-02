package HttpServer;

import HttpServer.Exceptions.ResponseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public abstract class HttpRequestHandler {

    public HttpRequestHandler(Logger logger) {
        this.logger = logger;
    }

    public abstract boolean canHandle(HttpRequest request);

    protected abstract HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) throws ResponseException;

    public void handle(Socket socket, HttpRequest request, HttpServerInfo serverInfo) throws ResponseException {
        HttpResponse response = this.getResponse(request, serverInfo);
        String respondWith = response.getResponseString();
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream());
            out.print(respondWith);
        } catch (IOException e) {
            //this.logger.severe(e.toString());
        } finally {
            // The output stream must be closed before the socket gets closed
            if (out != null) {
                out.close();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }

    protected Logger logger;
}
