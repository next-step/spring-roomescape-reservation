package roomescape.domain.reservation.domain.model;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class ReservationGuestName {

    private final String value;

    public ReservationGuestName(final String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value of name must not be null");
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationGuestName that = (ReservationGuestName) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
