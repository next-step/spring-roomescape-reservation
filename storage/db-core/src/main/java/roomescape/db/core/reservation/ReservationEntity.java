package roomescape.db.core.reservation;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.theme.ThemeId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReservationEntity {

    private final Long id;
    private final Long themeId;
    private final Long timeId;
    private final String name;
    private final LocalDate date;
    private final ActiveStatus activeStatus;
    private final LocalDateTime deletedAt;
    private final LocalDateTime createdAt;

    @Builder
    private ReservationEntity(
            final Long id,
            final Long themeId,
            final Long timeId,
            final String name,
            final LocalDate date,
            final ActiveStatus activeStatus,
            final LocalDateTime deletedAt,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.themeId = themeId;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.activeStatus = activeStatus;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
    }

    public static ReservationEntity fromModel(Reservation reservation) {
        return builder()
                .id(reservation.getId())
                .themeId(reservation.getThemeId().value())
                .timeId(reservation.getTimeId().value())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .activeStatus(reservation.getActiveStatus())
                .deletedAt(reservation.getDeletedAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public Reservation toModel() {
        return Reservation.builder()
                .id(this.id)
                .themeId(new ThemeId(this.themeId))
                .timeId(new ReservationTimeId(this.timeId))
                .name(new ReservationGuestName(this.name))
                .date(new ReservationDate(this.date))
                .activeStatus(this.activeStatus)
                .deletedAt(this.deletedAt)
                .createdAt(this.createdAt)
                .build();
    }
}
