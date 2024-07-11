package roomescape.domain.reservation.infrastructure;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservation.domain.ReservationStatus;
import roomescape.domain.reservationtime.infrastructure.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReservationEntity {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeEntity time;
    private final ReservationStatus status;
    private final LocalDateTime canceledAt;
    private final LocalDateTime createdAt;

    @Builder
    private ReservationEntity(
            final Long id,
            final String name,
            final LocalDate date,
            final ReservationTimeEntity time,
            final ReservationStatus status,
            final LocalDateTime canceledAt,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
        this.canceledAt = canceledAt;
        this.createdAt = createdAt;
    }

    public static ReservationEntity fromModel(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .time(ReservationTimeEntity.fromModel(reservation.getTime()))
                .status(reservation.getStatus())
                .canceledAt(reservation.getCanceledAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public Reservation toModel() {
        return Reservation.builder()
                .id(this.id)
                .name(new ReservationGuestName(this.name))
                .date(new ReservationDate(this.date))
                .time(this.time.toModel())
                .status(this.status)
                .canceledAt(this.canceledAt)
                .createdAt(this.createdAt)
                .build();
    }
}
