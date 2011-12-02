package HttpServer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class HttpResponse {

    protected HttpResponse() {

    }

    public HttpResponse(String protocolVersion, int statusCode, String reasonPhrase, List<HttpResponseHeader> responseHeaders, String body) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    public String getResponseString() {
        StringBuilder builder = new StringBuilder();
        this.buildStatusLine(builder);
        this.buildHeaders(builder);
        this.buildBody(builder);
        return builder.toString();
    }

    public StringBuilder buildStatusLine(StringBuilder builder) {
        builder.append(this.getProtocolVersion());
        builder.append(" ");
        builder.append(this.statusCode);
        builder.append(" ");
        builder.append(this.reasonPhrase);
        builder.append("\r\n");
        return builder;
    }

    public void buildHeaders(StringBuilder builder) {
        this.buildDateHeader(builder);
        for (HttpResponseHeader header : this.responseHeaders) {
            builder.append(header.getName());
            builder.append(": ");
            builder.append(header.getValue());
            builder.append("\r\n");
        }
        if (body != null && body.length() > 0) {
            builder.append("Content-Length: ");
            builder.append(this.body.length());
            builder.append("\r\n");
        }
    }

    public void buildDateHeader(StringBuilder builder) {
        Date date = new Date();
        builder.append("Date: ");
        builder.append(this.getFormattedDate(date));
        builder.append("\r\n");
    }

    public String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        return dateFormat.format(date);
    }

    public void buildBody(StringBuilder builder) {
        if (body != null && body.length() > 0) {
            builder.append("\r\n");
            builder.append(this.body);
            builder.append("\r\n");
        }
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public List<HttpResponseHeader> getResponseHeaders() {
        return responseHeaders;
    }

    public void addResponseHeader(HttpResponseHeader responseHeader) {
        responseHeaders.add(responseHeader);
    }

    public void setResponseHeaders(List<HttpResponseHeader> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String protocolVersion;
    private int statusCode;
    private String reasonPhrase;
    private List<HttpResponseHeader> responseHeaders;
    private String body;
}
