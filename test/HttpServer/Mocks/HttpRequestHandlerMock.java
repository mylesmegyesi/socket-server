package HttpServer.Mocks;

import HttpServer.HttpRequest;
import HttpServer.HttpRequestHandler;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHandlerMock implements HttpRequestHandler {

    public HttpRequestHandlerMock(boolean canHandle) {
        this.canHandle = canHandle;
    }

    public boolean canHandle(HttpRequest request) {
        return this.canHandle;
    }

    public void handle(HttpRequest request) {
        this.handleCalledCount++;
    }

    public int getHandleCalledCount() {
        return this.handleCalledCount;
    }

    public void resetHandleCalledCount() {
        this.handleCalledCount = 0;
    }

    public void setCanHandle(boolean canHandle) {
        this.canHandle = canHandle;
    }

    private int handleCalledCount = 0;
    private boolean canHandle = true;
}
