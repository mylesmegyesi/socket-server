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
public class FileTest {

    File fileRequestHandler;
    HttpRequest request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetHandler(this.getClass().getName(), Level.SEVERE);
        this.request = new HttpRequest("GET", "/someFile.html", "HTTP/1.1", new ArrayList<HttpRequestHeader>(), null, null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenFileExists() throws Exception {
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertTrue("Can handle should return true when the file exists.", this.fileRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleWhenFileDoesNotExists() throws Exception {
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setFileExists(false);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        HttpRequest request = new HttpRequest("POST", "/someFile.html", "HTTP/1.1", new ArrayList<HttpRequestHeader>(), null, null);
        FileInformationMock fileInfo = new FileInformationMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canHandle(request));
    }

    private Logger logger;
}
