package HttpServer.Mocks;

import HttpServer.HttpRequest;
import HttpServer.HttpResponse;
import HttpServer.HttpServerInfo;

import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHandlerMock extends HttpRequestHandlerBaseMock {

    public HttpRequestHandlerMock(boolean canHandle, Logger logger) {
        super(logger);
        this.canHandle = canHandle;
    }

    public boolean canHandle(HttpRequest request) {
        return this.canHandle;
    }

    public HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) {
        this.handleCalledCount++;
        return new HttpResponseMock();
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
