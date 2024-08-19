package roomescape.core.domain.reservation;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.common.ClockHolder;
import roomescape.core.domain.reservation.exception.ReservationException;
import roomescape.core.domain.reservationtime.ReservationTime;

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
    private final ActiveStatus activeStatus;
    private final LocalDateTime deletedAt;
    private final LocalDateTime createdAt;

    @Builder
    public Reservation(
            final Long id,
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTime time,
            final ActiveStatus activeStatus,
            final LocalDateTime deletedAt,
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
        if (Objects.isNull(activeStatus)) {
            throw ReservationException.nullField("activeStatus");
        }
        if (Objects.isNull(createdAt)) {
            throw ReservationException.nullField("createdAt");
        }

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.activeStatus = activeStatus;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTime time,
            final ClockHolder clockHolder
    ) {
        return builder()
                .name(name)
                .date(date)
                .time(time)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation delete(final ClockHolder clockHolder) {
        return Reservation.builder()
                .id(this.id)
                .name(this.name)
                .date(this.date)
                .time(this.time)
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(this.createdAt)
                .deletedAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public LocalDate fetchReservationDate() {
        return this.date.getValue();
    }

    public LocalTime fetchReservationTime() {
        return this.time.getStartAt();
    }

    public boolean isActive() {
        return this.activeStatus == ActiveStatus.ACTIVE;
    }

    public boolean canceled() {
        return this.activeStatus == ActiveStatus.DELETED;
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
