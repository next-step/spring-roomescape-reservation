package roomescape.global.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatUtils {

    private DateTimeFormatUtils() {
    }

    public static String toIsoLocal(final LocalDateTime target) {
        return target.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String toIsoLocal(final LocalTime target) {
        return target.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
