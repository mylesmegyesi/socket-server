package HttpServer.Mocks;

import HttpServer.HttpRequestDispatcherFactory;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: mylesmegyesi
 * Date: 11/30/11
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestDispatcherFactoryMock extends HttpRequestDispatcherFactory {

    @Override
    public HttpRequestDispatcherMock create(Socket socket, Logger logger) {
        return new HttpRequestDispatcherMock(socket, logger);
    }

}
