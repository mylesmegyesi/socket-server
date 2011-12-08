package SocketServer.Mocks;

/**
 * Author: Myles Megyesi
 */
public class RuntimeMock extends SocketServer.Utility.Runtime {

    public int addCalledCount = 0;
    public int removeCalledCount = 0;
    public boolean throwOnIllegalStateAdd;
    public boolean throwIllegalArgOnAdd;
    public boolean throwIllegalStateOnRemove;

    public RuntimeMock(Runtime runtime, boolean throwOnIllegalStateAdd, boolean throwIllegalArgOnAdd, boolean throwIllegalStateOnRemove) {
        super(runtime);
        this.throwOnIllegalStateAdd = throwOnIllegalStateAdd;
        this.throwIllegalArgOnAdd = throwIllegalArgOnAdd;
        this.throwIllegalStateOnRemove = throwIllegalStateOnRemove;
    }

    @Override
    public void addShutdownHook(Thread hook) {
        this.addCalledCount++;
        if (this.throwIllegalArgOnAdd) {
            throw new IllegalArgumentException();
        }
        if (this.throwOnIllegalStateAdd) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean removeShutdownHook(Thread hook) {
        this.removeCalledCount++;
        if (this.throwIllegalStateOnRemove) {
            throw new IllegalStateException();
        }
        return true;
    }
}
