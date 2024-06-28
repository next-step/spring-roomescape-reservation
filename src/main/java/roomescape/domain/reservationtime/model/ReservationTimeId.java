package roomescape.domain.reservationtime.model;

import lombok.Getter;
import roomescape.domain.reservationtime.exception.ReservationTimeException;

import java.util.Objects;

@Getter
public class ReservationTimeId {

    private final Long value;

    public ReservationTimeId(final Long value) {
        if (Objects.isNull(value)) {
            throw ReservationTimeException.nullField("value");
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationTimeId that = (ReservationTimeId) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
