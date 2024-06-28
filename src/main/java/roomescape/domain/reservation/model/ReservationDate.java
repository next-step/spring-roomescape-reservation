package roomescape.domain.reservation.model;

import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ReservationDate {

    private final LocalDate value;

    public ReservationDate(final LocalDate value) {
        if (Objects.isNull("value")) {
            throw ReservationException.nullField("value");
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationDate that = (ReservationDate) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
