package SocketServer.Mocks;

import SocketServer.ShutDownHandler;
import SocketServer.SocketServer;
import SocketServer.Utility.Runtime;

/**
 * Author: Myles Megyesi
 */
public class ShutDownHandlerMock extends ShutDownHandler {

    public int addCalledCount = 0;
    public int removeCalledCount = 0;

    public ShutDownHandlerMock(SocketServer socketServer, Runtime runtime) {
        super(socketServer, runtime);
    }

    @Override
    public void addShutDownHook() {
        this.addCalledCount++;
    }

    @Override
    public boolean removeShutDownHook() {
        this.removeCalledCount++;
        return true;
    }

}
