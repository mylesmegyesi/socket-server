package SocketServer;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public interface RequestDispatcherFactory {

    public Runnable create(Socket socket);

}
