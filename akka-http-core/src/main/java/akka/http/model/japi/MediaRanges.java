package akka.http.model.japi;

public interface MediaRanges {
    final MediaRange All = akka.http.model.MediaRanges.$times$div$times();
    final MediaRange AllApplication = akka.http.model.MediaRanges.application$div$times();
    final MediaRange AllAudio = akka.http.model.MediaRanges.audio$div$times();
    final MediaRange AllImage = akka.http.model.MediaRanges.image$div$times();
    final MediaRange AllMessage = akka.http.model.MediaRanges.message$div$times();
    final MediaRange AllMultipart = akka.http.model.MediaRanges.multipart$div$times();
    final MediaRange AllText = akka.http.model.MediaRanges.text$div$times();
    final MediaRange AllVideo= akka.http.model.MediaRanges.video$div$times();
}
