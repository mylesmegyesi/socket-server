package SocketServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import static junit.framework.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class SocketTest {

    Socket socket;
    int port = 8080;

    @Before
    public void setUp() throws Exception {
        this.socket = new Socket(this.port);
    }

    @After
    public void tearDown() throws Exception {
        this.socket.close();
    }

    @Test
    public void opensTheSocket() throws Exception {
        this.socket.open();
        assertFalse(isPortAvailable(this.port));
    }

    @Test
    public void closesTheSocket() throws Exception {
        this.socket.open();
        this.socket.close();
        assertTrue(isPortAvailable(this.port));
    }

    @Test
    public void isBoundReturnsFalseBeforeOpen() throws Exception {
        assertFalse(this.socket.isBound());
    }

    @Test
    public void isBoundReturnsTrueAfterOpen() throws Exception {
        this.socket.open();
        assertTrue(this.socket.isBound());
    }

    @Test
    public void isBoundReturnsFalseAfterClose() throws Exception {
        this.socket.open();
        assertTrue(this.socket.isBound());
    }

    @Test
    public void acceptReturnsNullIfNotOpen() throws Exception {
        assertEquals(null, this.socket.accept());
    }

    @Test
    public void acceptRespondsToConnections() throws Exception {
        this.socket.open();
        this.sendMessage(this.port, "message");
        java.net.Socket socket = this.socket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertEquals("message", reader.readLine());
    }

    private boolean isPortAvailable(int port) {
        ServerSocket ss = null;
        boolean ret = false;
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress("0.0.0.0", port));
            ret = true;
        } catch (IOException e) {
        } finally {
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException e) {
            }
        }
        return ret;
    }

    private void sendMessage(int port, String message) {
        java.net.Socket socket = null;
        OutputStream out = null;
        try {
            socket = new java.net.Socket();
            socket.connect(new InetSocketAddress("localhost", port), 100);
            out = socket.getOutputStream();
            out.write(message.getBytes());
        } catch (IOException e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
