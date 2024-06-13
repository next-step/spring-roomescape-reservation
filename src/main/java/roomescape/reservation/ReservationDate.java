package roomescape.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationDate {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private final LocalDate date;

    public ReservationDate(String date) {
        this.date = LocalDate.parse(date);
    }

    @JsonValue
    @Override
    public String toString() {
        return date.format(formatter);
    }
}
