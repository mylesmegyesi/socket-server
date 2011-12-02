package HttpServer.HttpRequestHandlers;

import HttpServer.Exceptions.ResponseException;
import HttpServer.*;
import HttpServer.Utility.FileInformation;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
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
        return fileInfo.fileExists(this.directoryToServe, request.getRequestUri());
    }

    @Override
    protected HttpResponse getResponse(HttpRequest request, HttpServerInfo serverInfo) throws ResponseException {
        HttpResponse response = new HttpResponse("HTTP/1.1", 200, "OK", new ArrayList<HttpResponseHeader>(), null);
        java.io.File fileToServe = new java.io.File(this.directoryToServe, request.getRequestUri());
        String mimeType = new MimetypesFileTypeMap().getContentType(fileToServe.getName());
        response.addResponseHeader(new HttpResponseHeader("Content-Type", mimeType));
        response.addResponseHeader(new HttpResponseHeader("Last-Modified", response.getFormattedDate(new Date(fileToServe.lastModified()))));
        FileReader reader = null;
        try {
            reader = new FileReader(fileToServe);
        } catch (FileNotFoundException e) {
            throw new ResponseException(e.getMessage());
        }
        StringBuilder builder = new StringBuilder();
        try {
            while (reader.ready()) {
                builder.append((char) reader.read());
            }
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
        response.setBody(builder.toString());
        return response;
    }

    private FileInformation fileInfo;
    private String directoryToServe;
}
