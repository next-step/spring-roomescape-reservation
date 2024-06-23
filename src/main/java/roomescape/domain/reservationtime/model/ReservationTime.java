package roomescape.domain.reservationtime.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;

import java.time.LocalTime;
import java.util.Objects;

@Getter
public class ReservationTime {

    private final ReservationTimeId id;
    private final LocalTime startAt;

    @Builder
    private ReservationTime(final ReservationTimeId id, final LocalTime startAt) {
        if (Objects.isNull(startAt)) {
            throw ReservationException.nullField("startAt");
        }

        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime from(final LocalTime startAt) {
        return ReservationTime.builder()
                .startAt(startAt)
                .build();
    }

    public Long getIdValue() {
        return this.id.getValue();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ReservationTime that = (ReservationTime) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
