package HttpServer;

import HttpServer.Exceptions.BadRequestException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParser {

    protected HttpRequestParser() {

    }

    public HttpRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        String rawRequest = inputStreamToString(inputStream);
        Scanner scanner = new Scanner(rawRequest);
        String firstLine = scanner.nextLine();
        String[] requestItems = firstLine.split(" ");
        if (requestItems.length != 3) {
            throw new BadRequestException();
        }
        String action = requestItems[0].trim();
        String requestUri =  requestItems[1].trim();
        String protocol = requestItems[2].trim();
        List<HttpRequestHeader> requestHeaders = new ArrayList<HttpRequestHeader>();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine().trim();
            if (nextLine.equals("")) {
                break; //no more headers
            }
            requestHeaders.add(parseHeader(nextLine));
        }
        String body = "";
        if (scanner.hasNextLine()) {
            body = scanner.nextLine();
        }
        return new HttpRequest(action, requestUri, protocol, requestHeaders, body, rawRequest);
    }

    private HttpRequestHeader parseHeader(String headerLine) throws BadRequestException {
        String[] headerItems = headerLine.split(":");
        if (headerItems.length != 2) {
            throw new BadRequestException();
        }
        return new HttpRequestHeader(headerItems[0].trim(), headerItems[1].trim());
    }

    private String inputStreamToString(InputStream input) throws IOException {
        StringBuilder buffer = new StringBuilder();
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
