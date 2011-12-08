package SocketServer;

import SocketServer.Utility.Runtime;

/**
 * Author: Myles Megyesi
 */
public class ShutDownHandler extends Thread {

    public SocketServer socketServer;
    public Runtime runtime;

    public ShutDownHandler(SocketServer socketServer, Runtime runtime) {
        this.socketServer = socketServer;
        this.runtime = runtime;
    }

    @Override
    public void run() {
        if (this.socketServer != null) {
            this.socketServer.stop();
        }
    }

    public void addShutDownHook() {
        try {
            this.runtime.addShutdownHook(this);
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    public boolean removeShutDownHook() {
        try {
            return this.runtime.removeShutdownHook(this);
        } catch (IllegalStateException e) {
        }
        return false;
    }

}
