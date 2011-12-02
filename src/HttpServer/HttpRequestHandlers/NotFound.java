package HttpServer.HttpRequestHandlers;

import HttpServer.*;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class NotFound extends HttpRequestHandler {

    public NotFound(Logger logger) {
        super(logger);
    }

    public boolean canHandle(HttpRequest request) {
        return true;
    }

    public HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) {
        return new HttpResponse("HTTP/1.1", 404, "Not Found", new ArrayList<HttpResponseHeader>(), null);
    }
}
