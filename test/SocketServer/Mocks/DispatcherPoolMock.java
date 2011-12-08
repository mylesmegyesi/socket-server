package SocketServer.Mocks;

import SocketServer.DispatcherPool;

import java.util.concurrent.ExecutorService;

/**
 * Author: Myles Megyesi
 */
public class DispatcherPoolMock extends DispatcherPool {

    public int executeCalledCount = 0;
    public int shutdownCalledCount = 0;

    public DispatcherPoolMock(ExecutorService threadPool) {
        super(threadPool);
    }

    @Override
    public void execute(Runnable runnable) {
        this.executeCalledCount++;
    }

    @Override
    public void shutdown() {
        this.shutdownCalledCount++;
    }
}
