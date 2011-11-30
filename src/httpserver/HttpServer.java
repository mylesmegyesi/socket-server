package HttpServer;

import HttpServer.HttpRequestHandlers.FileNotFound;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class HttpServer extends Thread {

    public HttpServer(Logger logger, HttpRequestDispatcherFactory dispatcherFactory, HttpRequestParserFactory parserFactory) {
        this.logger = logger;
        this.dispatcherFactory = dispatcherFactory;
        this.parserFactory = parserFactory;
    }

    public HttpServer(String directory, int port, Logger logger, HttpRequestDispatcherFactory dispatcherFactory) {
        this.setDirectory(directory);
        this.setPort(port);
        this.logger = logger;
        this.dispatcherFactory = dispatcherFactory;
    }

    public String getDirectory() {
        synchronized (this.directoryLock) {
            return this.directory;
        }
    }

    public void setDirectory(String directory) {
        synchronized (this.directoryLock) {
            this.directory = directory;
        }
    }

    public int getPort() {
        synchronized (this.portLock) {
            return this.port;
        }
    }

    public void setPort(int port) {
        synchronized (this.portLock) {
            this.port = port;
        }
    }

    public boolean serverIsListening() {
        synchronized (this.editSocketLock) {
            return (this.serverSocket != null && this.serverSocket.isBound());
        }
    }

    public HttpRequestDispatcherFactory getDispatcherFactory() {
        synchronized (this.dispatcherFactoryLock) {
            return this.dispatcherFactory;
        }
    }

    public void setDispatcherFactory(HttpRequestDispatcherFactory dispatcherFactory) {
        synchronized (this.dispatcherFactoryLock) {
            this.dispatcherFactory = dispatcherFactory;
        }
    }

    public HttpRequestParserFactory getParserFactory() {
        synchronized (this.parserFactoryLock) {
            return this.parserFactory;
        }
    }

    public void setParserFactory(HttpRequestParserFactory parserFactory) {
        synchronized (this.parserFactoryLock) {
            this.parserFactory = parserFactory;
        }
    }

    public void run() {
        String directory = this.getDirectory();
        initializeSocket();
        while (serverIsListening()) {
            this.setDispatcherPool(Executors.newCachedThreadPool());
            try {
                Socket socket = this.serverSocket.accept();
                this.getDispatcherPool().execute(this.getDispatcherFactory().create(socket, this.getParserFactory().create(), new ArrayList<HttpRequestHandler>(), new FileNotFound(), this.logger));
            } catch (SocketException e) {
                this.logger.info("Server stopped listening.");
            } catch (NullPointerException e) {
                this.logger.info("Server stopped listening.");
            } catch (Exception e) {
                this.logger.severe(String.format("Server died for an unknown reason: %s", e.toString()));
            }
        }
    }

    public void stopListening() {
        synchronized (this.editSocketLock) {
            try {
                if (this.serverSocket != null) {
                    this.serverSocket.close();
                }
            } catch (IOException e) {
            } finally {
                this.serverSocket = null;
            }
        }
        synchronized (this.dispatcherPoolLock) {
            if (this.dispatcherPool != null) {
                this.dispatcherPool.shutdown();
                try {
                    while (!this.dispatcherPool.isTerminated()) {
                        this.dispatcherPool.awaitTermination(500, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                }
            }
        }
        if (this.isAlive()) {
            try {
                this.join();
            } catch (InterruptedException e) {
            }
        }
    }

    private void initializeSocket() {
        synchronized (this.editSocketLock) {
            try {
                this.serverSocket = createServerSocket(this.getPort());
                assert (this.serverSocket != null);
                assert (this.serverSocket.isBound());
                this.logger.info(String.format("Server listening at %s", this.serverSocket.getLocalSocketAddress()));
            } catch (IOException e) {
                this.logger.severe(String.format("Failed to start server: %s", e.toString()));
            }
        }
    }

    private ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    private ExecutorService getDispatcherPool() {
        synchronized (this.dispatcherPoolLock) {
            return dispatcherPool;
        }
    }

    private void setDispatcherPool(ExecutorService dispatcherPool) {
        synchronized (this.dispatcherPoolLock) {
            this.dispatcherPool = dispatcherPool;
        }
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("HttpServer");
        logger.setLevel(Level.ALL);
        StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        HttpServer server = new HttpServer(logger, new HttpRequestDispatcherFactory(), new HttpRequestParserFactory());
        Runtime.getRuntime().addShutdownHook(new HttpServerShutdownHandler(server));
        server.start();
        while (true) {

        }
    }

    private Logger logger;
    private String directory = ".";
    private int port = 8080;
    private ServerSocket serverSocket = null;
    private HttpRequestDispatcherFactory dispatcherFactory;
    private HttpRequestParserFactory parserFactory;
    private ExecutorService dispatcherPool;
    private final Object portLock = new Object();
    private final Object directoryLock = new Object();
    private final Object editSocketLock = new Object();
    private final Object dispatcherFactoryLock = new Object();
    private final Object parserFactoryLock = new Object();
    private final Object dispatcherPoolLock = new Object();
}

