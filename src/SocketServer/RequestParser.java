package SocketServer;

import SocketServer.Exceptions.BadRequestException;

import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public interface RequestParser {

    public Request parse(InputStream inputStream) throws BadRequestException;
}
