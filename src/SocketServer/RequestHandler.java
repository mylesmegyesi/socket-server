package SocketServer;


import SocketServer.Exceptions.BadRequestException;
import SocketServer.Exceptions.ResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandler implements Runnable {

    public RequestHandler(Socket socket, RequestParser requestParser, List<Responder> responders, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responders = responders;
        this.logger = logger;
    }

    public void addHandler(Responder responder) {
        this.responders.add(responder);
    }

    public void run() {
        this.logger.info(String.format("Received request from %s:%d", this.socket.getInetAddress(), this.socket.getLocalPort()));
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = this.socket.getInputStream();
            outputStream = this.socket.getOutputStream();
        } catch (IOException e) {
            this.logger.severe("Could not open the input and output streams of the socket.");
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }
            return;
        }
        Request request;
        try {
            request = this.requestParser.parse(inputStream);
        } catch (BadRequestException e) {
            this.logger.severe(String.format("Unable to parse request. %s %s", "\n", e.getMessage()));
            return;
        }
        for (Responder responder : this.responders) {
            if (responder.canHandle(request)) {
                try {
                    responder.handle(outputStream, request);
                } catch (ResponseException e) {
                    this.logger.severe(String.format("%s failed to generate response: %s", responder.getClass().getName(), e.getMessage()));
                }
            }
        }
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {

        }
    }

    private Socket socket;
    private RequestParser requestParser;
    private List<Responder> responders;
    private Responder defaultHandler;
    private Logger logger;
}
