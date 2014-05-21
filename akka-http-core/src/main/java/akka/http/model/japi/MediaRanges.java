package akka.http.model.japi;

import java.util.Map;

public final class MediaRanges {
    private MediaRanges() {}

    public static final MediaRange All = akka.http.model.MediaRanges.$times$div$times();
    public static final MediaRange AllApplication = akka.http.model.MediaRanges.application$div$times();
    public static final MediaRange AllAudio = akka.http.model.MediaRanges.audio$div$times();
    public static final MediaRange AllImage = akka.http.model.MediaRanges.image$div$times();
    public static final MediaRange AllMessage = akka.http.model.MediaRanges.message$div$times();
    public static final MediaRange AllMultipart = akka.http.model.MediaRanges.multipart$div$times();
    public static final MediaRange AllText = akka.http.model.MediaRanges.text$div$times();
    public static final MediaRange AllVideo= akka.http.model.MediaRanges.video$div$times();

    public static MediaRange custom(String mainType, Map<String, String> parameters) {
        return akka.http.model.MediaRange.custom(mainType, Util.convertMapToScala(parameters), 1.0f);
    }
    public static MediaRange create(MediaType mediaType) {
        return akka.http.model.MediaRange.apply((akka.http.model.MediaType) mediaType);
    }
    public static MediaRange create(MediaType mediaType, float qValue) {
        return akka.http.model.MediaRange.apply((akka.http.model.MediaType) mediaType, qValue);
    }
}
