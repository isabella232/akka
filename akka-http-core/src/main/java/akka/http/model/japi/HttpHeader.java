package akka.http.model.japi;

public abstract class HttpHeader {
    public abstract String name();
    public abstract String value();
    public abstract String lowercaseName();
    public abstract boolean is(String nameInLowerCase);
    public abstract boolean isNot(String nameInLowerCase);
}
