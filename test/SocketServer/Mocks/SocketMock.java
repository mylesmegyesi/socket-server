package SocketServer.Mocks;

import SocketServer.Socket;

import java.io.IOException;
import java.net.SocketException;

/**
 * Author: Myles Megyesi
 */
public class SocketMock extends Socket {

    public int openCalledCount = 0;
    public int isBoundCalledCount = 0;
    public int acceptCalledCount = 0;
    public int closeCalledCount = 0;
    public boolean closeThrows;
    public boolean acceptThrows;

    public SocketMock(int port, boolean closeThrows, boolean acceptThrows) {
        super(port);
        this.closeThrows = closeThrows;
        this.acceptThrows = acceptThrows;
    }

    @Override
    public void open() {
        this.openCalledCount++;
    }

    @Override
    public void close() throws IOException {
        this.closeCalledCount++;
        if (this.closeThrows) {
            throw new IOException();
        }
    }

    @Override
    public boolean isBound() {
        this.isBoundCalledCount++;
        if (!this.acceptThrows) {
            return this.openCalledCount > 0 && this.closeCalledCount <= 0;
        }
        return true;
    }

    @Override
    public java.net.Socket accept() throws SocketException {
        this.acceptCalledCount++;
        if (this.acceptThrows && this.closeCalledCount > 0) {
            throw new SocketException();
        }
        return null;
    }
}
