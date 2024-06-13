package roomescape.domain.reservation.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationDate {

    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        this.date = date;
    }

    public String getFormatted(String pattern) {
        return this.date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public LocalDate getDate() {
        return date;
    }
}
