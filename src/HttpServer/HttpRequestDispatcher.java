package HttpServer;


import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpRequestDispatcher implements Runnable {

    protected HttpRequestDispatcher() {

    }

    public HttpRequestDispatcher(Socket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    public void run() {
        String request = "";
        try {
            request = inputStreamToString(socket.getInputStream());
        } catch (IOException e) {
            // report bad request
        }
        this.logger.info("Received Request");
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream());
            out.println(request);
        } catch (IOException e) {
            //this.logger.severe(e.toString());
        } finally {
            // The output stream must be closed before the socket gets closed
            if (out != null) {
                out.close();
            }
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
            }
        }

    }

    private String inputStreamToString(InputStream input) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        if (!reader.ready()) {
            buffer.append((char) reader.read());
        }
        while (reader.ready()) {
            buffer.append((char) reader.read());
        }
        return buffer.toString();
    }

    private Socket socket;
    private Logger logger;
}
