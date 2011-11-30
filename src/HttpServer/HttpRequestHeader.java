package HttpServer;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHeader {
//    Accept("Accept"),
//    AcceptCharset("Accept-Charset"),
//    AcceptEncoding("Accept-Encoding"),
//    AcceptLanguage("Accept-Language"),
//    Authorization("Authorization"),
//    CacheControl("Cache-Control"),
//    Connection("Connection"),
//    Cookie("Cookie"),
//    ContentLength("Content-Length"),
//    ContentMD5("Content-MD5"),
//    ContentType("Content-Type"),
//    Date("Date"),
//    Expect("Expect"),
//    From("From"),
//    Host("Host"),
//    IfMatch("If-Match"),
//    IfModifiedSince("If-Modified-Since"),
//    IfNoneMatch("If-None-Match"),
//    IfRange("If-Range"),
//    IfUnmodifiedSince("If-Unmodified-Since"),
//    MaxForwards("Max-Forwards"),
//    Pragma("Pragma"),
//    ProxyAuthorization("Proxy-Authorization"),
//    Range("Range"),
//    Referer("Referer"),
//    TE("TE"),
//    Upgrade("Upgrade"),
//    UserAgent("User-Agent"),
//    Via("Via"),
//    XRequestedWith("X-Requested-With"),
//    XDoNotTrack("X-Do-Not-Track"),
//    DNT("DNT"),
//    XForwardedFor("X-Forwarded-For");

    public HttpRequestHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String name;
    private String value;
}
