package akka.http.model.japi;

public interface HttpHeader {
    String name();
    String value();
    String lowercaseName();
    boolean is(String nameInLowerCase);
    boolean isNot(String nameInLowerCase);
}
