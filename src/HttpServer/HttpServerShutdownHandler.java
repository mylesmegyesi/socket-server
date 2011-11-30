package HttpServer;

public class HttpServerShutdownHandler extends Thread {

    private HttpServer server = null;

    public HttpServerShutdownHandler(HttpServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        if (server != null) {
            server.stopListening();
        }
    }

}
