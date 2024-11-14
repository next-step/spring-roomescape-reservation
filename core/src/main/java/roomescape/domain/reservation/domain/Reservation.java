package roomescape.domain.reservation.domain;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.common.exception.Assert;
import roomescape.domain.reservation.exception.ReservationAlreadyCanceledException;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Reservation {

    private final Long id;
    private final ThemeId themeId;
    private final ReservationDate date;
    private final ReservationTimeId timeId;

    private final ReservationGuestName name;
    private final ReservationStatus status;
    private final LocalDateTime reservedAt;
    private final LocalDateTime canceledAt;

    @Builder
    private Reservation(
            final Long id,
            final ThemeId themeId,
            final ReservationDate date,
            final ReservationTimeId timeId,
            final ReservationGuestName name,
            final ReservationStatus status,
            final LocalDateTime reservedAt,
            final LocalDateTime canceledAt
    ) {
        Assert.notNullField(themeId, "themeId");
        Assert.notNullField(date, "date");
        Assert.notNullField(timeId, "timeId");
        Assert.notNullField(name, "name");
        Assert.notNullField(status, "status");
        Assert.notNullField(reservedAt, "reservedAt");

        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.timeId = timeId;
        this.name = name;
        this.status = status;
        this.reservedAt = reservedAt;
        this.canceledAt = canceledAt;
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
                .status(ReservationStatus.CONFIRMED)
                .reservedAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public Reservation cancel(final ClockHolder clockHolder) {
        if (isCanceled()) {
            throw new ReservationAlreadyCanceledException();
        }

        return Reservation.builder()
                .id(this.id)
                .themeId(this.themeId)
                .timeId(this.timeId)
                .name(this.name)
                .date(this.date)
                .status(ReservationStatus.CANCELED)
                .reservedAt(this.reservedAt)
                .canceledAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public boolean isConfirmed() {
        return this.status.isConfirmed();
    }

    public boolean isCanceled() {
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
