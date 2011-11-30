package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {

    public HttpRequestParser() {

    }

    public HttpRequest parse(InputStream inputStream) {
        return new HttpRequest();
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
}
