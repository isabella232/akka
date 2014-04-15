package akka.http.model.japi;

import akka.http.model.HttpProtocols;

public interface HttpProtocol {
    String value();

    public static final HttpProtocol Http10 = HttpProtocols.HTTP$div1$u002E0();
    public static final HttpProtocol Http11 = HttpProtocols.HTTP$div1$u002E1();
}
