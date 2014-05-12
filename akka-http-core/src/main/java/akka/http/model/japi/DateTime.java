package akka.http.model.japi;

import akka.japi.Option;

public abstract class DateTime {
    public abstract int year();
    public abstract int month();
    public abstract int day();
    public abstract int hour();
    public abstract int minute();
    public abstract int second();
    public abstract int weekday();
    public abstract long clicks();
    public abstract boolean isLeapYear();

    public abstract String weekdayStr();
    public abstract String monthStr();
    public abstract String toIsoDateString();
    public abstract String toIsoDateTimeString();
    public abstract String toIsoLikeDateTimeString();
    public abstract String toRfc1123DateTimeString();

    public static DateTime now() {
        return akka.http.util.DateTime.now();
    }
    public static Option<DateTime> fromIsoDateTimeString(String isoDateTimeString) {
        return Util.convertOption(akka.http.util.DateTime.fromIsoDateTimeString(isoDateTimeString));
    }
    public static DateTime create(long clicks) {
        return akka.http.util.DateTime.apply(clicks);
    }
    public static DateTime create(int year, int month, int day, int hour, int minute, int second) {
        return akka.http.util.DateTime.apply(year, month, day, hour, minute, second);
    }
}
