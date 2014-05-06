package akka.http.model.japi;

import akka.japi.Option;

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

    class C {
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
}
