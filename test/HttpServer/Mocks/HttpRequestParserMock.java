package HttpServer.Mocks;

import HttpServer.HttpRequest;
import HttpServer.HttpRequestParser;

import java.io.InputStream;

public class HttpRequestParserMock extends HttpRequestParser {

    @Override
    public HttpRequest parse(InputStream input) {
        this.calledCount++;
        return new HttpRequestMock();
    }

    public int getCalledCount() {
        return this.calledCount;
    }

    public void resetCalledCount() {
        this.calledCount = 0;
    }

    private int calledCount = 0;


}
