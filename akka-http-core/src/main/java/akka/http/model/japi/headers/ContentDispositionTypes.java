package akka.http.model.japi.headers;

public final class ContentDispositionTypes {
    private ContentDispositionTypes() {}

    public static final ContentDispositionType inline = akka.http.model.headers.ContentDispositionTypes.inline$.MODULE$;
    public static final ContentDispositionType attachment = akka.http.model.headers.ContentDispositionTypes.attachment$.MODULE$;
    public static final ContentDispositionType form_data = akka.http.model.headers.ContentDispositionTypes.form$minusdata$.MODULE$;
    public static ContentDispositionType Ext(String name) {
        return new akka.http.model.headers.ContentDispositionTypes.Ext(name);
    }
}
