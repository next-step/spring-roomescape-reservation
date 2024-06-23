package roomescape.domain.reservation.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Reservation {

    private final Long id;
    private final ReservationGuestName name;
    private final ReservationDateTime dateTime;
    private final ReservationStatus status;
    private final LocalDateTime canceledAt;
    private final LocalDateTime createdAt;

    @Builder
    private Reservation(
            final Long id,
            final ReservationGuestName name,
            final ReservationDateTime dateTime,
            final ReservationStatus status,
            final LocalDateTime canceledAt,
            final LocalDateTime createdAt
    ) {
        if (Objects.isNull(name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(dateTime)) {
            throw ReservationException.nullField("dateTime");
        }
        if (Objects.isNull(status)) {
            throw ReservationException.nullField("status");
        }
        if (Objects.isNull(createdAt)) {
            throw ReservationException.nullField("createdAt");
        }

        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.status = status;
        this.canceledAt = canceledAt;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationDateTime dateTime,
            final ClockHolder clockHolder
    ) {
        return Reservation.builder()
                .name(name)
                .dateTime(dateTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation cancel(final ClockHolder clockHolder) {
        return Reservation.builder()
                .id(this.id)
                .name(this.name)
                .dateTime(this.dateTime)
                .status(ReservationStatus.CANCELED)
                .createdAt(this.createdAt)
                .canceledAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public LocalDate fetchReservationDate() {
        return this.dateTime.getDate();
    }

    public LocalTime fetchReservationTime() {
        return this.dateTime.getTime();
    }

    public boolean matchesId(final Long id) {
        return this.id.equals(id);
    }

    public boolean matchesName(final ReservationGuestName name) {
        return this.name.equals(name);
    }

    public boolean matchesTimestamp(final ReservationDateTime timeStamp) {
        return this.dateTime.equals(timeStamp);
    }

    public boolean isActive() {
        return !canceled();
    }

    public boolean canceled() {
        return this.status.isCanceled();
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
