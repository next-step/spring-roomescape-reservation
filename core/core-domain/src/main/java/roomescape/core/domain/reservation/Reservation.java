package roomescape.core.domain.reservation;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.common.ClockHolder;
import roomescape.core.domain.reservation.exception.ReservationException;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Reservation {

    private final Long id;
    private final ThemeId themeId;
    private final ReservationTimeId timeId;
    private final ReservationGuestName name;
    private final ReservationDate date;
    private final ActiveStatus activeStatus;
    private final LocalDateTime deletedAt;
    private final LocalDateTime createdAt;

    @Builder
    public Reservation(
            final Long id,
            final ThemeId themeId,
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTimeId timeId,
            final ActiveStatus activeStatus,
            final LocalDateTime deletedAt,
            final LocalDateTime createdAt
    ) {
        if (Objects.isNull(themeId)) {
            throw ReservationException.nullField("themeId");
        }
        if (Objects.isNull(name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(date)) {
            throw ReservationException.nullField("date");
        }
        if (Objects.isNull(timeId)) {
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
        this.timeId = timeId;
        this.themeId = themeId;
        this.activeStatus = activeStatus;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTime time,
            final Theme theme,
            final ClockHolder clockHolder
    ) {
        return Reservation.builder()
                .themeId(theme.getId())
                .timeId(time.getId())
                .name(name)
                .date(date)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation delete(final ClockHolder clockHolder) {
        return Reservation.builder()
                .id(this.id)
                .themeId(this.themeId)
                .timeId(this.timeId)
                .name(this.name)
                .date(this.date)
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(this.createdAt)
                .deletedAt(clockHolder.getCurrentSeoulTime())
                .build();
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
