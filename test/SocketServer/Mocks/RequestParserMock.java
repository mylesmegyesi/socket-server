package SocketServer.Mocks;

import SocketServer.Exceptions.BadRequestException;
import SocketServer.Request;
import SocketServer.RequestParser;

import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class RequestParserMock implements RequestParser{
    public RequestParserMock() {
    }

    public Request parse(InputStream inputStream) throws BadRequestException {
        this.calledCount++;
        return new Request();
    }

    public int getCalledCount() {
        return calledCount;
    }

    public void resetCalledCount() {
        this.calledCount = 0;
    }

    private int calledCount = 0;

}
