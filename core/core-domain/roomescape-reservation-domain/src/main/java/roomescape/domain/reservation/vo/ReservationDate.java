package roomescape.domain.reservation.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationDate(LocalDate date) {

    public String getFormatted(String pattern) {
        return this.date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
