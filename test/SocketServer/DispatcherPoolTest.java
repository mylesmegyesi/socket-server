package SocketServer;

import SocketServer.Mocks.ExecutorServiceMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Author: Myles Megyesi
 */
public class DispatcherPoolTest {

    DispatcherPool dispatcherPool;
    ExecutorServiceMock executorServiceMock;

    @Before
    public void setUp() throws Exception {
        this.executorServiceMock = new ExecutorServiceMock(false);
        this.dispatcherPool = new DispatcherPool(this.executorServiceMock);
    }

    @After
    public void tearDown() throws Exception {
        this.dispatcherPool = null;
        this.executorServiceMock = null;
    }

    @Test
    public void executeCallsExecute() throws Exception {
        this.dispatcherPool.execute(null);
        assertEquals(1, this.executorServiceMock.executeCalledCount);
    }

    @Test
    public void shutdownCallsShutdown() throws Exception {
        this.dispatcherPool.shutdown();
        assertEquals(1, this.executorServiceMock.shutdownCalledCount);
    }

    @Test
    public void isTerminatedCalledDuringShutdown() throws Exception {
        this.dispatcherPool.shutdown();
        assertEquals(2, this.executorServiceMock.isTerminatedCalledCount);
    }

    @Test
    public void awaitCalledDuringShutdown() throws Exception {
        this.dispatcherPool.shutdown();
        assertEquals(1, this.executorServiceMock.awaitCalledCount);
    }

    @Test
    public void awaitDoesNotThrowInterruptedException() throws Exception {
        this.executorServiceMock = new ExecutorServiceMock(true);
        this.dispatcherPool = new DispatcherPool(this.executorServiceMock);
        try {
            this.dispatcherPool.shutdown();
        } catch (Exception e) {
            fail();
        }

    }
}
