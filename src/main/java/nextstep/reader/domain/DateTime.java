package nextstep.reader.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTime dateTime = (DateTime) o;
        return Objects.equals(date, dateTime.date) && Objects.equals(
            time,
            dateTime.time
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
