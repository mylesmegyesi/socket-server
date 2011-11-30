package HttpServer;

import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;

public class HttpRequest {

    protected HttpRequest() {

    }

    public HttpRequest(String action, String requestUri, String protocolVersion, List<HttpRequestHeader> requestHeaders, String body, String rawRequest) {
        this.action = action;
        this.requestUri = requestUri;
        this.protocolVersion = protocolVersion;
        this.requestHeaders = requestHeaders;
        this.body = body;
        this.rawRequest = rawRequest;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public List<HttpRequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void addHeader(HttpRequestHeader requestHeader) {
        this.requestHeaders.add(requestHeader);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRawRequest() {
        return rawRequest;
    }

    public void setRawRequest(String rawRequest) {
        this.rawRequest = rawRequest;
    }

    private String action;
    private String requestUri;
    private String protocolVersion;
    private String body;
    private String rawRequest;
    private List<HttpRequestHeader> requestHeaders;


}
