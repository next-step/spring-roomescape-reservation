package roomescape.domain.reservation.domain;

import lombok.Getter;
import roomescape.domain.common.exception.Assert;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ReservationDate {

    private final LocalDate value;

    public ReservationDate(final LocalDate value) {
        Assert.notNullField(value, "value of reservation date");
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
