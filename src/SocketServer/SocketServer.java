package SocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SocketServer {

    public SocketServer(int port, RequestHandlerFactory handlerFactory, Logger logger) {
        this.port = port;
        this.logger = logger;
        this.handlerFactory = handlerFactory;
    }

    public void startListeningInBackground() {
        synchronized (this.startUpShutdownLock) {
            try {
                this.initialize();
            } catch (IOException e) {
                return;
            }
            final SocketServer socketServer = this;
            this.serverThread = new Thread(new Runnable() {
                public void run() {
                    socketServer.listen();
                }
            });
            this.serverThread.start();
        }
    }

    public void startListening() {
        synchronized (this.startUpShutdownLock) {
            try {
                this.initialize();
            } catch (IOException e) {
                return;
            }
        }
        this.listen();
    }

    private void initialize() throws IOException {
        Runtime.getRuntime().addShutdownHook(new SocketServerShutdownHandler(this));
        try {
            this.serverSocket = createServerSocket(this.port);
            this.logger.info(String.format("SocketServer listening at %s", this.serverSocket.getLocalSocketAddress()));
        } catch (IOException e) {
            this.logger.severe(String.format("Failed to start SocketServer: %s", e.toString()));
            throw e;
        }
    }

    private ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.0.0", port));
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    private void listen() {
        ExecutorService dispatcherPool = Executors.newCachedThreadPool();
        while (this.portIsBound()) {
            try {
                Socket socket = this.serverSocket.accept();
                dispatcherPool.execute(this.handlerFactory.create(socket));
            } catch (SocketException e) {
                this.logger.info("SocketServer stopped listening.");
            } catch (NullPointerException e) {
                this.logger.info("SocketServer stopped listening.");
            } catch (Exception e) {
                this.logger.severe(String.format("SocketServer died for an unknown reason: %s", e.toString()));
            }
        }
        dispatcherPool.shutdown();
        try {
            while (!dispatcherPool.isTerminated()) {
                dispatcherPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
        }
    }

    private boolean portIsBound() {
        return this.serverSocket != null && this.serverSocket.isBound();
    }

    public void stopListening() {
        synchronized (this.startUpShutdownLock) {
            if (!this.portIsBound()) {
                 return;
            }
            this.logger.info("SocketServer is shutting down.");
            try {
                if (this.serverSocket != null) {
                    this.serverSocket.close();
                }
            } catch (IOException e) {
            } finally {
                this.serverSocket = null;
            }
            if (this.serverThread != null) {
                if (this.serverThread.isAlive()) {
                    try {
                        this.serverThread.join();
                    } catch (InterruptedException e) {
                    }
                }
                this.serverThread = null;
            }
            this.logger.info("SocketServer was shutdown.");
        }
    }

    public int getPort() {
        return port;
    }

    private int port;
    private ServerSocket serverSocket;
    private Thread serverThread = null;
    private RequestHandlerFactory handlerFactory;
    private final Object startUpShutdownLock = new Object();
    private Logger logger;
}

