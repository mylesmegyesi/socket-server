package SocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * Author: Myles Megyesi
 */
public class Socket {

    public int port;

    public Socket(int port) {
        this.port = port;
    }

    public void open() throws IOException {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress("0.0.0.0", this.port));
        this.serverSocket.setReuseAddress(true);
    }

    public void close() throws IOException {
        if (this.serverSocket != null) {
            this.serverSocket.close();
        }
        this.serverSocket = null;
    }

    public java.net.Socket accept() throws IOException {
        if (this.serverSocket == null) {
            return null;
        }
        return this.serverSocket.accept();
    }

    public boolean isBound() {
        return this.serverSocket != null && this.serverSocket.isBound();
    }

    private ServerSocket serverSocket;

}
