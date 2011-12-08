package SocketServer.Mocks;

import SocketServer.RequestDispatcherFactory;
import SocketServer.SocketListener;

/**
 * Author: Myles Megyesi
 */
public class SocketListenerMock extends SocketListener {

    public int startCalledCount = 0;
    public int stopListeningCalledCount = 0;
    public boolean stopListeningThrows;

    public SocketListenerMock(int port, RequestDispatcherFactory requestDispatcherFactory, boolean stopListeningThrows) {
        super(port, requestDispatcherFactory);
        this.stopListeningThrows = stopListeningThrows;
    }

    @Override
    public void start() {
        this.startCalledCount++;
    }

    @Override
    public void stopListening() throws InterruptedException {
        this.stopListeningCalledCount++;
        if (this.stopListeningThrows) {
            throw new InterruptedException();
        }
    }
}
