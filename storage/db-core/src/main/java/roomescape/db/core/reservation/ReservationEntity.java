package roomescape.db.core.reservation;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.db.core.reservatiotime.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReservationEntity {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeEntity time;
    private final ActiveStatus activeStatus;
    private final LocalDateTime deletedAt;
    private final LocalDateTime createdAt;

    @Builder
    private ReservationEntity(
            final Long id,
            final String name,
            final LocalDate date,
            final ReservationTimeEntity time,
            final ActiveStatus activeStatus,
            final LocalDateTime deletedAt,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.activeStatus = activeStatus;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
    }

    public static ReservationEntity fromModel(Reservation reservation) {
        return builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .time(ReservationTimeEntity.fromModel(reservation.getTime()))
                .activeStatus(reservation.getActiveStatus())
                .deletedAt(reservation.getDeletedAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public Reservation toModel() {
        return Reservation.builder()
                .id(this.id)
                .name(new ReservationGuestName(this.name))
                .date(new ReservationDate(this.date))
                .time(this.time.toModel())
                .activeStatus(this.activeStatus)
                .deletedAt(this.deletedAt)
                .createdAt(this.createdAt)
                .build();
    }
}
