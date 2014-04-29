package akka.http.model.japi;

public interface DateTime {
    int year();
    int month();
    int day();
    int hour();
    int minute();
    int second();
    int weekday();
    long clicks();
    boolean isLeapYear();

    String weekdayStr();
    String monthStr();
    String toIsoDateString();
    String toIsoDateTimeString();
    String toIsoLikeDateTimeString();
    String toRfc1123DateTimeString();
}
