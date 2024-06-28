package roomescape.domain.reservation.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class ReservationDateTime {

    private final LocalDateTime value;

    public ReservationDateTime(LocalDateTime value) {
        this.value = value;
    }

    public static ReservationDateTime of(LocalDate date, LocalTime time) {
        return new ReservationDateTime(LocalDateTime.of(date, time));
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationDateTime that = (ReservationDateTime) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public LocalDate getDate() {
        return this.value.toLocalDate();
    }

    public LocalTime getTime() {
        return this.value.toLocalTime();
    }

}
