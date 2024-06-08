package roomescape.domain.reservation.domain.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Reservation {

    private Long id;
    private final ReservationGuestName name;
    private final ReservationTimeStamp timeStamp;
    private final ReservationStatus status;
    private final LocalDateTime canceledAt;
    private final LocalDateTime createdAt;

    @Builder
    private Reservation(
            final Long id,
            final ReservationGuestName name,
            final ReservationTimeStamp timeStamp,
            final ReservationStatus status,
            final LocalDateTime canceledAt,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.status = status;
        this.canceledAt = canceledAt;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationTimeStamp timeStamp,
            final ClockHolder clockHolder
    ) {
        return Reservation.builder()
                .name(name)
                .timeStamp(timeStamp)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation cancel(final ClockHolder clockHolder) {
        return Reservation.builder()
                .id(this.id)
                .name(this.name)
                .timeStamp(this.timeStamp)
                .status(ReservationStatus.CANCELED)
                .createdAt(this.createdAt)
                .canceledAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public LocalDate fetchReservationDate() {
        return this.timeStamp.getDate();
    }

    public LocalTime fetchReservationTime() {
        return this.timeStamp.getTime();
    }

    public boolean matchesId(final Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Reservation that = (Reservation) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}