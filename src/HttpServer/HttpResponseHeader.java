package HttpServer;

/**
 * Author: Myles Megyesi
 */
public class HttpResponseHeader {

    private HttpResponseHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return this.name;
    }

    private String name;
    private String value;
}
