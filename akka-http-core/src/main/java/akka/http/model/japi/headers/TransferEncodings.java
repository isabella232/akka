/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

public final class TransferEncodings {
    private TransferEncodings() {}

    public static final TransferEncoding CHUNKED  = akka.http.model.headers.TransferEncodings.chunked$.MODULE$;
    public static final TransferEncoding COMPRESS = akka.http.model.headers.TransferEncodings.compress$.MODULE$;
    public static final TransferEncoding DEFLATE  = akka.http.model.headers.TransferEncodings.deflate$.MODULE$;
    public static final TransferEncoding GZIP     = akka.http.model.headers.TransferEncodings.gzip$.MODULE$;
}
