package SocketServer.Mocks;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Author: Myles Megyesi
 */
public class ExecutorServiceMock implements ExecutorService {

    public int executeCalledCount = 0;
    public int shutdownCalledCount = 0;
    public int isTerminatedCalledCount = 0;
    public int awaitCalledCount = 0;
    boolean throwOnAwait;

    public ExecutorServiceMock(boolean throwOnAwait) {
        this.throwOnAwait = throwOnAwait;
    }

    public void shutdown() {
        this.shutdownCalledCount++;
    }

    public List<Runnable> shutdownNow() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isShutdown() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isTerminated() {
        this.isTerminatedCalledCount++;
        return this.isTerminatedCalledCount != 1;
    }

    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        this.awaitCalledCount++;
        if (this.throwOnAwait) {
            throw new InterruptedException();
        }
        return true;
    }

    public <T> Future<T> submit(Callable<T> tCallable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Future<?> submit(Runnable runnable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables) throws InterruptedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) throws InterruptedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> callables) throws InterruptedException, ExecutionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void execute(Runnable runnable) {
        this.executeCalledCount++;
    }
}
