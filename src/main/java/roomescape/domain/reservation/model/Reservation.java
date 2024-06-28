package roomescape.domain.reservation.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Reservation {

    private final Long id;
    private final ReservationGuestName name;
    private final ReservationDate date;
    private final ReservationTime time;
    private final ReservationStatus status;
    private final LocalDateTime canceledAt;
    private final LocalDateTime createdAt;

    @Builder
    public Reservation(
            final Long id,
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTime time,
            final ReservationStatus status,
            final LocalDateTime canceledAt,
            final LocalDateTime createdAt
    ) {
        if (Objects.isNull(name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(date)) {
            throw ReservationException.nullField("date");
        }
        if (Objects.isNull(time)) {
            throw ReservationException.nullField("time");
        }
        if (Objects.isNull(status)) {
            throw ReservationException.nullField("status");
        }
        if (Objects.isNull(createdAt)) {
            throw ReservationException.nullField("createdAt");
        }

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
        this.canceledAt = canceledAt;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTime time,
            final ClockHolder clockHolder
    ) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation cancel(final ClockHolder clockHolder) {
        return Reservation.builder()
                .id(this.id)
                .name(this.name)
                .date(this.date)
                .time(this.time)
                .status(ReservationStatus.CANCELED)
                .createdAt(this.createdAt)
                .canceledAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public LocalDate fetchReservationDate() {
        return this.date.getValue();
    }

    public LocalTime fetchReservationTime() {
        return this.time.getStartAt();
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
