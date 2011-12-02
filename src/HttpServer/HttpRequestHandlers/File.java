package HttpServer.HttpRequestHandlers;

import HttpServer.*;
import HttpServer.Utility.FileInformation;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class File extends HttpRequestHandler {

    public File(String directoryToServe, FileInformation fileInfo, Logger logger) {
        super(logger);
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public FileInformation getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInformation fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        if (!request.getAction().equals("GET")) {
            return false;
        }
        return fileInfo.FileExists(this.directoryToServe, request.getRequestUri());
    }

    @Override
    protected HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) {
        java.io.File fileToServe = new java.io.File(this.directoryToServe, request.getRequestUri());
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileToServe));
        } catch (FileNotFoundException e) {
        }
        String mimeType = null;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        } catch (IOException e) {
        }
        List<HttpResponseHeader> responseHeaders = new ArrayList<HttpResponseHeader>();
        responseHeaders.add(new HttpResponseHeader("Content-Type", mimeType));
        FileReader reader = null;
        try {
            reader = new FileReader(fileToServe);
        } catch (FileNotFoundException e) {
        }
        StringBuilder builder = new StringBuilder();
        try {
            if (reader != null) {
                while (reader.ready()) {
                    builder.append((char) reader.read());
                }
            }
        } catch (IOException e) {
        }
        return new HttpResponse("HTTP/1.1", 200, "OK", responseHeaders, builder.toString());
    }

    private FileInformation fileInfo;
    private String directoryToServe;
}
