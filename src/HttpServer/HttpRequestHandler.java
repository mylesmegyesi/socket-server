package HttpServer;

public interface HttpRequestHandler {

    public boolean canHandle(HttpRequest request);

    public void handle(HttpRequest request);
}
