package akka.http.model.japi;

public interface MediaRanges {
    MediaRange All = akka.http.model.MediaRanges.$times$div$times();
    MediaRange AllApplication = akka.http.model.MediaRanges.application$div$times();
    MediaRange AllAudio = akka.http.model.MediaRanges.audio$div$times();
    MediaRange AllImage = akka.http.model.MediaRanges.image$div$times();
    MediaRange AllMessage = akka.http.model.MediaRanges.message$div$times();
    MediaRange AllMultipart = akka.http.model.MediaRanges.multipart$div$times();
    MediaRange AllText = akka.http.model.MediaRanges.text$div$times();
    MediaRange AllVideo= akka.http.model.MediaRanges.video$div$times();
}
