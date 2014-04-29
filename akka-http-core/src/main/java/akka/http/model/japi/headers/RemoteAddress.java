package akka.http.model.japi.headers;

import akka.japi.Option;

import java.net.InetAddress;

public interface RemoteAddress {
    boolean isUnknown();

    Option<InetAddress> getAddress();
}
