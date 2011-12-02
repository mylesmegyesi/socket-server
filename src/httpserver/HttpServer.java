package HttpServer;

import HttpServer.HttpRequestHandlers.Directory;
import HttpServer.HttpRequestHandlers.File;
import HttpServer.HttpRequestHandlers.NotFound;
import HttpServer.Utility.FileInformation;
import HttpServer.Utility.Logging;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {

    public HttpServer(HttpRequestDispatcherFactory dispatcherFactory, HttpRequestParserFactory parserFactory, Logger logger) {
        this.init(dispatcherFactory, parserFactory, logger);
    }

    public HttpServer(String directory, int port, HttpRequestDispatcherFactory dispatcherFactory, HttpRequestParserFactory parserFactory, Logger logger) {
        this.setDirectory(directory);
        this.setPort(port);
        this.init(dispatcherFactory, parserFactory, logger);
    }

    private void init(HttpRequestDispatcherFactory dispatcherFactory, HttpRequestParserFactory parserFactory, Logger logger) {
        this.logger = logger;
        this.dispatcherFactory = dispatcherFactory;
        this.parserFactory = parserFactory;
    }

    public void startListeningInBackground() {
        if (this.getServerThread() != null || this.serverIsListening()) {
            return;
        }
        final HttpServer server = this;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                server.run();
            }
        });
        this.setServerThread(thread);
        this.getServerThread().start();
        while (!this.isServerInitialized()) {
        }
    }

    public void startListening() {
        if (this.getServerThread() != null || this.serverIsListening()) {
            return;
        }
        this.run();
    }

    private void run() {
        this.setServerInitialized(false);
        String directory = this.getDirectory();
        initialize();
        if (this.didServerFailToStart()) {
            return;
        }
        ExecutorService dispatcherPool = Executors.newCachedThreadPool();
        while (this.serverIsListening()) {
            try {
                Socket socket = this.serverSocket.accept();
                dispatcherPool.execute(this.getDispatcherFactory().create(socket, this.getParserFactory().create(), this.requestHandlers, new NotFound(this.logger), new HttpServerInfo(), this.logger));
            } catch (SocketException e) {
                this.logger.info("Server stopped listening.");
            } catch (NullPointerException e) {
                this.logger.info("Server stopped listening.");
            } catch (Exception e) {
                this.logger.severe(String.format("Server died for an unknown reason: %s", e.toString()));
            }
        }
        dispatcherPool.shutdown();
        try {
            while (!dispatcherPool.isTerminated()) {
                dispatcherPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
        }
        this.setServerInitialized(false);
    }

    public static void main(String[] args) {
        Logger logger = Logging.getLoggerAndSetHandler(HttpServer.class.getName(), Level.ALL);
        HttpServer server = new HttpServer(new HttpRequestDispatcherFactory(), new HttpRequestParserFactory(), logger);
        server.addRequestHandler(new Directory(server.getDirectory(), new FileInformation(), logger));
        server.addRequestHandler(new File(server.getDirectory(), new FileInformation(), logger));
        Runtime.getRuntime().addShutdownHook(new HttpServerShutdownHandler(server));
        server.startListening();
    }

    public void waitForServerShutdown() {
        if (this.getServerThread() == null || !this.serverIsListening()) {
            return;
        }
        try {
            this.getServerThread().join();
        } catch (InterruptedException e) {
        }
    }

    public void stopListening() {
        this.logger.info("Server is shutting down.");
        if (!this.isServerInitialized()) {
            return;
        }
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
        synchronized (this.serverThreadLock) {
            if (this.serverThread != null) {
                if (this.serverThread.isAlive()) {
                    try {
                        this.serverThread.join();
                    } catch (InterruptedException e) {
                    }
                }
                this.serverThread = null;
            }
        }
        this.logger.info("Server was shutdown.");
    }

    public void addRequestHandler(HttpRequestHandler requestHandler) {
        requestHandlers.add(requestHandler);
    }

    public boolean serverIsListening() {
        synchronized (this.editSocketLock) {
            return (this.serverSocket != null && this.serverSocket.isBound());
        }
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

    public boolean isServerStarting() {
        synchronized (this.serverStartingLock) {
            return serverStarting;
        }
    }

    public boolean didServerFailToStart() {
        synchronized (this.serverFailedToStartLock) {
            return this.serverFailedToStart;
        }
    }

    private void initialize() {
        synchronized (this.editSocketLock) {
            try {
                this.serverSocket = createServerSocket(this.getPort());
                this.logger.info(String.format("Server listening at %s", this.serverSocket.getLocalSocketAddress()));
                this.setServerFailedToStart(false);
            } catch (IOException e) {
                this.logger.severe(String.format("Failed to start server: %s", e.toString()));
                this.setServerFailedToStart(true);
            } finally {
                this.setServerInitialized(true);
            }
        }
    }

    private ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    private Thread getServerThread() {
        synchronized (this.serverThreadLock) {
            return this.serverThread;
        }
    }

    public void setServerThread(Thread serverThread) {
        synchronized (this.serverThreadLock) {
            this.serverThread = serverThread;
        }
    }

    private void setServerStarting(boolean serverStarting) {
        synchronized (this.serverStartingLock) {
            this.serverStarting = serverStarting;
        }
    }

    private boolean isServerInitialized() {
        synchronized (this.serverInitializedLock) {
            return serverInitialized;
        }
    }

    private void setServerInitialized(boolean serverInitialized) {
        synchronized (this.serverInitializedLock) {
            this.serverInitialized = serverInitialized;
        }
    }

    private void setServerFailedToStart(boolean serverFailedToStart) {
        synchronized (this.serverFailedToStartLock) {
            this.serverFailedToStart = serverFailedToStart;
        }
    }

    private String directory = "public";
    private final Object directoryLock = new Object();
    private int port = 8080;
    private final Object portLock = new Object();
    private Thread serverThread = null;
    private final Object serverThreadLock = new Object();
    private ServerSocket serverSocket = null;
    private final Object editSocketLock = new Object();
    private HttpRequestDispatcherFactory dispatcherFactory;
    private final Object dispatcherFactoryLock = new Object();
    private HttpRequestParserFactory parserFactory;
    private final Object parserFactoryLock = new Object();
    private boolean serverStarting = false;
    private final Object serverStartingLock = new Object();
    private boolean serverFailedToStart = false;
    private final Object serverFailedToStartLock = new Object();
    private boolean serverInitialized = false;
    private final Object serverInitializedLock = new Object();
    private Logger logger;
    private List<HttpRequestHandler> requestHandlers = new ArrayList<HttpRequestHandler>();
}

