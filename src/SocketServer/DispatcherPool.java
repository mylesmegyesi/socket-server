package SocketServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: Myles Megyesi
 */
public class DispatcherPool {

    public ExecutorService threadPool;

    public DispatcherPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void execute(Runnable runnable) {
        this.threadPool.execute(runnable);
    }

    public void shutdown() {
        this.threadPool.shutdown();
        this.waitForTermination();
        this.threadPool = null;
    }

    private void waitForTermination() {
        try {
            while (!this.threadPool.isTerminated()) {
                this.threadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
        }
    }

}
