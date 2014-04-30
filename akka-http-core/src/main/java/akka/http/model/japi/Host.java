package akka.http.model.japi;

import akka.japi.Option;

import java.net.InetAddress;

public interface Host {
    String address();
    boolean isEmpty();
    boolean isIPv4();
    boolean isIPv6();
    boolean isNamedHost();

    Iterable<InetAddress> getInetAddresses();
}
