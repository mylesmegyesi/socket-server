package SocketServer.Mocks;

import SocketServer.RequestDispatcherFactory;

import java.net.Socket;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcherFactoryMock implements RequestDispatcherFactory {


    public Runnable create(Socket socket) {
        return new RequestHandlerMock();
    }
}
