package SocketServer;

import SocketServer.Utility.Runtime;

import java.io.IOException;

public class SocketServer {

    public ShutDownHandler shutDownHandler;
    public SocketListener socketListener;

    public SocketServer(int port, RequestDispatcherFactory dispatcherFactory) {
        this.shutDownHandler = new ShutDownHandler(this, new Runtime(java.lang.Runtime.getRuntime()));
        this.socketListener = new SocketListener(port, dispatcherFactory);
    }

    public SocketServer(ShutDownHandler shutdownHandler, SocketListener socketListener) {
        this.shutDownHandler = shutdownHandler;
        this.shutDownHandler.socketServer = this;
        this.socketListener = socketListener;
    }

    public void start() throws IOException {
        synchronized (this.startUpShutdownLock) {
            this.shutDownHandler.addShutDownHook();
            this.socketListener.start();
        }
    }

    public void stop() {
        synchronized (this.startUpShutdownLock) {
            try {
                this.socketListener.stopListening();
            } catch (InterruptedException e) {
            }
            this.shutDownHandler.removeShutDownHook();
        }
    }

    private final Object startUpShutdownLock = new Object();
}

