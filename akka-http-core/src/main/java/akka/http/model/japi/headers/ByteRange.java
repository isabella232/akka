package akka.http.model.japi.headers;

import akka.japi.Option;

public interface ByteRange {
    boolean isSlice();
    boolean isFromOffset();
    boolean isSuffix();

    Option<Long> getSliceFirst();
    Option<Long> getSliceLast();
    Option<Long> getOffset();
    Option<Long> getSuffixLength();
}
