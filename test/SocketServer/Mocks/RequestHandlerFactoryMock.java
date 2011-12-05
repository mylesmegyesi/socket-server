package SocketServer.Mocks;

import SocketServer.RequestHandlerFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerFactoryMock implements RequestHandlerFactory {


    public Runnable create(Socket socket) {
        return new RequestHandlerMock();
    }
}
