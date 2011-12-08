package SocketServer.Mocks;

import SocketServer.SocketServer;

/**
 * Author: Myles Megyesi
 */
public class SocketServerMock extends SocketServer {

    public int stopCalledCount = 0;

    public SocketServerMock() {
        super(new ShutDownHandlerMock(null, null), null);
    }

    @Override
    public void stop() {
        this.stopCalledCount++;
    }
}
