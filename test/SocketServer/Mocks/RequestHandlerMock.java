package SocketServer.Mocks;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerMock implements Runnable {


    public static int getCalledCount() {
        return calledCount;
    }

    public static void resetCalledCount() {
        RequestHandlerMock.calledCount = 0;
    }

    private static int calledCount = 0;

    public void run() {
        calledCount++;
    }
}
