package akka.http.model.japi.headers;

public final class ContentDispositionTypes {
    private ContentDispositionTypes() {}

    public static final ContentDispositionType inline = akka.http.model.headers.ContentDispositionType.inline$.MODULE$;
    public static final ContentDispositionType attachment = akka.http.model.headers.ContentDispositionType.attachment$.MODULE$;
    public static final ContentDispositionType form_data = akka.http.model.headers.ContentDispositionType.form$minusdata$.MODULE$;
    public static ContentDispositionType Ext(String name) {
        return new akka.http.model.headers.ContentDispositionType.Ext(name);
    }
}
