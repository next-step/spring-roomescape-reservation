package roomescape.domain.reservation.application.dto;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.theme.domain.Theme;

@Getter
public class ReservationTimeThemeDto {

    private final Reservation reservation;
    private final ReservationTime time;
    private final Theme theme;

    @Builder
    private ReservationTimeThemeDto(
            final Reservation reservation,
            final ReservationTime time,
            final Theme theme
    ) {
        if (!reservation.getThemeId().equals(theme.getId())) {
            throw new IllegalArgumentException("theme ids are not same");
        }
        if (!reservation.getTimeId().equals(time.getId())) {
            throw new IllegalArgumentException("time ids are not same");
        }
        this.reservation = reservation;
        this.time = time;
        this.theme = theme;
    }

    public static ReservationTimeThemeDto of(
            final Reservation reservation,
            final ReservationTime time,
            final Theme theme
    ) {
        return ReservationTimeThemeDto.builder()
                .reservation(reservation)
                .time(time)
                .theme(theme)
                .build();
    }
}
