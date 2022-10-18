package nextstep.domain.reservation.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(Long id,
                          Long scheduleId,
                          LocalDate date,
                          LocalTime time,
                          String name) {
    public Reservation withId(Long id) {
        return new Reservation(id, scheduleId, date, time, name);
    }

    public boolean isScheduleEqual(Long scheduleId) {
        return Objects.equals(this.scheduleId, scheduleId);
    }

    public boolean isDateEqual(LocalDate date) {
        return this.date.isEqual(date);
    }

    public boolean isTimeEqual(LocalTime time) {
        return this.time.equals(time);
    }
}
