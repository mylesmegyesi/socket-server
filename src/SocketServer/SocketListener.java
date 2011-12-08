package SocketServer;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Author: Myles Megyesi
 */
public class SocketListener extends Thread {

    public Socket socket;
    public RequestDispatcherFactory requestDispatcherFactory;
    public DispatcherPool dispatcherPool;

    public SocketListener(int port, RequestDispatcherFactory requestDispatcherFactory) {
        this.socket = new Socket(port);
        this.requestDispatcherFactory = requestDispatcherFactory;
        this.dispatcherPool = new DispatcherPool(Executors.newCachedThreadPool());
    }

    public SocketListener(Socket socket, RequestDispatcherFactory requestDispatcherFactory, DispatcherPool dispatcherPool) {
        this.socket = socket;
        this.requestDispatcherFactory = requestDispatcherFactory;
        this.dispatcherPool = dispatcherPool;
    }

    @Override
    public void run() {
        try {
            startListening();
        } catch (IOException e) {
        }
    }

    private void startListening() throws IOException {
        initialize();
        acceptConnections();
    }

    public void stopListening() throws InterruptedException {
        this.clean();
        if (this.isAlive()) {
            this.join();
        }
    }

    private void initialize() throws IOException {
        this.socket.open();
    }

    private void acceptConnections() throws IOException {
        while (this.socket.isBound()) {
            java.net.Socket socket = this.socket.accept();
            this.dispatcherPool.execute(this.requestDispatcherFactory.create(socket));
        }
    }


    private void clean() {
        try {
            this.socket.close();
        } catch (IOException e) {
        }
        this.dispatcherPool.shutdown();
    }
}
