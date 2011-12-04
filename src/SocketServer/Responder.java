package SocketServer;

import SocketServer.Exceptions.ResponseException;

import java.io.OutputStream;

/**
 * Author: Myles Megyesi
 */
public interface Responder {

    public boolean canHandle(Request request);

    public void handle(OutputStream outputStream, Request request) throws ResponseException;
}
