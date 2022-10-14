package nextstep.reader.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime {

    private final LocalDate date;
    private final LocalTime time;

    public DateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
