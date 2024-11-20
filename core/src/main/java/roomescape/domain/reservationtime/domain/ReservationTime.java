package roomescape.domain.reservationtime.domain;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.common.exception.Assert;

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
        Assert.notNullField(startAt, "startAt");
        Assert.notNullField(createdAt, "createdAt");

        this.id = id;
        this.startAt = startAt;
        this.createdAt = createdAt;
    }

    public static ReservationTime defaultOf(final LocalTime startAt, ClockHolder clockHolder) {
        return builder()
                .startAt(startAt)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Long getIdValue() {
        if (Objects.isNull(this.id)) {
            return null;
        }
        return this.id.value();
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
