/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

public final class TransferEncodings {
    private TransferEncodings() {}

    public static final TransferEncoding chunked = akka.http.model.headers.TransferEncodings.chunked$.MODULE$;
    public static final TransferEncoding compress = akka.http.model.headers.TransferEncodings.compress$.MODULE$;
    public static final TransferEncoding deflate = akka.http.model.headers.TransferEncodings.deflate$.MODULE$;
    public static final TransferEncoding gzip = akka.http.model.headers.TransferEncodings.gzip$.MODULE$;
}
