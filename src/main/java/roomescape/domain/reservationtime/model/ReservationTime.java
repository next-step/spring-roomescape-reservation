package roomescape.domain.reservationtime.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservationtime.exception.ReservationTimeException;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class ReservationTime {

    private final ReservationTimeId id;
    private final LocalTime startAt;
    private final LocalDateTime createdAt;

    @Builder
    private ReservationTime(
            final ReservationTimeId id,
            final LocalTime startAt,
            final LocalDateTime createdAt
    ) {
        if (Objects.isNull(startAt)) {
            throw ReservationTimeException.nullField("startAt");
        }
        if (Objects.isNull(createdAt)) {
            throw ReservationTimeException.nullField("createdAt");
        }

        this.id = id;
        this.startAt = startAt;
        this.createdAt = createdAt;
    }

    public static ReservationTime defaultOf(final LocalTime startAt, ClockHolder clockHolder) {
        return ReservationTime.builder()
                .startAt(startAt)
                .createdAt(clockHolder.getCurrentSeoulTime())
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

    @Override
    public String toString() {
        return "ReservationTime{" +
               "id=" + id +
               ", startAt=" + startAt +
               ", createdAt=" + createdAt +
               '}';
    }
}
