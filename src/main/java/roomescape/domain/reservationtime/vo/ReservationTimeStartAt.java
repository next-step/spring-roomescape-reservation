package roomescape.domain.reservationtime.vo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTimeStartAt(LocalTime startAt) {

    public String getFormatted(String pattern) {
        return startAt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public boolean isBefore(LocalTime localTime) {
        return this.startAt.isBefore(localTime);
    }
}
