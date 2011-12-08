package SocketServer.Utility;

/**
 * Author: Myles Megyesi
 */
public class Runtime {

    java.lang.Runtime runtime;

    public Runtime(java.lang.Runtime runtime) {
        this.runtime = runtime;
    }

    public void addShutdownHook(Thread hook) {
        this.runtime.addShutdownHook(hook);
    }

    public boolean removeShutdownHook(Thread hook) {
        return this.runtime.removeShutdownHook(hook);
    }
}
