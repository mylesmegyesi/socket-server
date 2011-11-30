package HttpServer.Mocks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Author: Myles Megyesi
 */
public class SocketMock extends Socket {

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

}
