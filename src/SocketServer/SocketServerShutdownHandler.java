package SocketServer;

/**
 * Author: Myles Megyesi
 */
public class SocketServerShutdownHandler extends Thread {

    private SocketServer socketServer = null;

    public SocketServerShutdownHandler(SocketServer socketServer) {
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        if (socketServer != null) {
            socketServer.stopListening();
        }
    }

}
