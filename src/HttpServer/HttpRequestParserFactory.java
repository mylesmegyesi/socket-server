package HttpServer;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParserFactory {

    public HttpRequestParser create() {
        return new HttpRequestParser();
    }

}
