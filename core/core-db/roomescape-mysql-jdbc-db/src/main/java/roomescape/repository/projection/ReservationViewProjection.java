package roomescape.repository.projection;

import roomescape.domain.reservation.ReservationView;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationViewProjection {

    private final Long reservationId;

    private final String reservationName;

    private final LocalDate reservationDate;

    private final Long reservationTimeId;

    private final LocalTime reservationTimeStartAt;

    private final Long themeId;

    private final String themeName;

    public ReservationViewProjection(
            Long reservationId,
            String reservationName,
            LocalDate reservationDate,
            Long reservationTimeId,
            LocalTime reservationTimeStartAt,
            Long themeId,
            String themeName
    ) {
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.reservationTimeStartAt = reservationTimeStartAt;
        this.themeId = themeId;
        this.themeName = themeName;
    }
    
    public ReservationView toDomain() {
        return new ReservationView(
                new ReservationId(this.reservationId),
                new ReservationName(this.reservationName),
                new ReservationDate(this.reservationDate),
                new ReservationTimeId(this.reservationTimeId),
                new ReservationTimeStartAt(this.reservationTimeStartAt),
                new ThemeId(this.themeId),
                new ThemeName(this.themeName)
        );
    }
}
