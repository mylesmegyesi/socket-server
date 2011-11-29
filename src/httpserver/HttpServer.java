package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {

    public HttpServer() {

    }

    public HttpServer(String directory) {
        this.setDirectory(directory);
    }

    public HttpServer(int port) {
        this.setPort(port);
    }

    public HttpServer(String directory, int port) {
        this.setDirectory(directory);
        this.setPort(port);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        synchronized (socketLock) {
            serverSocket = new ServerSocket(this.getPort());
            serverSocket.setReuseAddress(true);
        }
    }

    public void stop() throws IOException {
        synchronized (socketLock) {
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
        }
    }

    private String directory = ".";
    private int port = 8080;
    private ServerSocket serverSocket;
    private Object socketLock = new Object();
}