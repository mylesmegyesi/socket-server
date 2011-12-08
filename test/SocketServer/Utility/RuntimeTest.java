package SocketServer.Utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class RuntimeTest {

    Runtime runtime;
    Thread thread;

    @Before
    public void setUp() throws Exception {
        this.thread = new Thread(new Runnable() {
            public void run() {
                return;
            }
        });
        this.runtime = new Runtime(java.lang.Runtime.getRuntime());
    }

    @After
    public void tearDown() throws Exception {
        this.runtime = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addsTheHookToTheRuntime() throws Exception {
        this.runtime.addShutdownHook(this.thread);
        this.runtime.addShutdownHook(this.thread);
    }

    @Test
    public void removesTheHook() throws Exception {
        this.runtime.addShutdownHook(this.thread);
        assertTrue(this.runtime.removeShutdownHook(this.thread));
    }

    @Test
    public void returnsFalseIfTheHookWasNeverAdded() throws Exception {
        assertFalse(this.runtime.removeShutdownHook(this.thread));
    }

}
