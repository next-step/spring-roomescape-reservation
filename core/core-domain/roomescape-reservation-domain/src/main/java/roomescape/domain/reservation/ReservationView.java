package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.util.validator.ObjectValidator;

public class ReservationView {

    private final ReservationId reservationId;

    private final ReservationName reservationName;

    private final ReservationDate reservationDate;

    private final ReservationTimeId reservationTimeId;

    private final ReservationTimeStartAt reservationTimeStartAt;

    private final ThemeId themeId;

    private final ThemeName themeName;

    public ReservationView(
            ReservationId reservationId,
            ReservationName reservationName,
            ReservationDate reservationDate,
            ReservationTimeId reservationTimeId,
            ReservationTimeStartAt reservationTimeStartAt,
            ThemeId themeId,
            ThemeName themeName
    ) {
        ObjectValidator.validateNotNull(reservationId, reservationName, reservationDate, reservationTimeId, reservationTimeStartAt);
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.reservationTimeStartAt = reservationTimeStartAt;
        this.themeId = themeId;
        this.themeName = themeName;
    }

    public Long getReservationId() {
        return this.reservationId.id();
    }

    public String getReservationName() {
        return this.reservationName.name();
    }

    public String getFormattedReservationDate(String pattern) {
        return this.reservationDate.getFormatted(pattern);
    }

    public String getFormattedReservationTimeStartAt(String pattern) {
        return this.reservationTimeStartAt.getFormatted(pattern);
    }

    public Long getReservationTimeId() {
        return this.reservationTimeId.id();
    }

    public Long getThemeId() {
        return themeId.id();
    }

    public String getThemeName() {
        return themeName.name();
    }
}
