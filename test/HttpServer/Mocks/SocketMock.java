package HttpServer.Mocks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

public class SocketMock extends Socket {

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

}
