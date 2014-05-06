package akka.http.model.japi;

import akka.http.model.Uri;

import java.net.InetAddress;
import java.nio.charset.Charset;

public interface Host {
    String address();
    boolean isEmpty();
    boolean isIPv4();
    boolean isIPv6();
    boolean isNamedHost();

    Iterable<InetAddress> getInetAddresses();

    public static abstract class C {
        public static final Host Empty = akka.http.model.Uri$Host$Empty$.MODULE$;
        public static final Uri.ParsingMode Strict = akka.http.model.Uri$ParsingMode$Strict$.MODULE$;
        public static final Uri.ParsingMode Relaxed = akka.http.model.Uri$ParsingMode$Relaxed$.MODULE$;
        public static final Uri.ParsingMode RelaxedWithRawQuery = akka.http.model.Uri$ParsingMode$RelaxedWithRawQuery$.MODULE$;

        public static Host create(String string) {
            return create(string, Uri.Host$.MODULE$.apply$default$2());
        }
        public static Host create(String string, Charset charset) {
            return Uri.Host$.MODULE$.apply(string, charset, Uri.Host$.MODULE$.apply$default$3());
        }
        public static Host create(String string, Charset charset, Uri.ParsingMode parsingMode) {
            return Uri.Host$.MODULE$.apply(string, charset, parsingMode);
        }
    }
}
