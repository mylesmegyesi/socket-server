package HttpServer.HttpRequestHandlers;

import HttpServer.HttpRequest;
import HttpServer.HttpRequestHeader;
import HttpServer.Utility.Logging;
import HttpServer.Utility.Mocks.FileInformationMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class DirectoryTest {

    Directory directoryRequestHandler;
    HttpRequest request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetHandler(this.getClass().getName(), Level.SEVERE);
        this.request = new HttpRequest("GET", "/someDir/", "HTTP/1.1", new ArrayList<HttpRequestHeader>(), null, null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenDirectoryExists() throws Exception {
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertTrue("Can handle should return true when the directory exists.", this.directoryRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleWhenDirectoryDoesNotExists() throws Exception {
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setDirectoryExists(false);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        HttpRequest request = new HttpRequest("POST", "/someDir/", "HTTP/1.1", new ArrayList<HttpRequestHeader>(), null, null);
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canHandle(request));
    }

    private Logger logger;
}
