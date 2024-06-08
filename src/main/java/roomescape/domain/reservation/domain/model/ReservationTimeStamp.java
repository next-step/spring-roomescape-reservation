package roomescape.domain.reservation.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReservationTimeStamp {

    private final LocalDateTime value;

    public ReservationTimeStamp(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationTimeStamp that = (ReservationTimeStamp) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
