package roomescape.domain.reservationtime.vo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTimeStartAt {

    private final LocalTime startAt;

    public ReservationTimeStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public String getFormatted(String pattern) {
        return startAt.format(DateTimeFormatter.ofPattern(pattern));
    }
}
