package SocketServer.Mocks;

import SocketServer.Exceptions.ResponseException;
import SocketServer.Request;
import SocketServer.Responder;

import java.io.OutputStream;

/**
 * Author: Myles Megyesi
 */
public class ResponderMock implements Responder{

    public ResponderMock(boolean canHandle) {
        this.canHandle = canHandle;
    }

    public boolean canHandle(Request request) {
        return canHandle;
    }

    public void handle(OutputStream outputStream, Request request) throws ResponseException {
        this.calledCount++;
    }

    public int getCalledCount() {
        return calledCount;
    }

    private boolean canHandle = false;
    private int calledCount = 0;
}
