package roomescape.application.service.command;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.theme.vo.ThemeId;

import java.time.LocalDate;

public class CreateReservationCommand {

    private final String reservationName;

    private final LocalDate reservationDate;

    private final Long reservationTimeId;

    private final Long themeId;

    public CreateReservationCommand(
            String reservationName,
            LocalDate reservationDate,
            Long reservationTimeId,
            Long themeId
    ) {
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.themeId = themeId;
    }

    public ReservationName getReservationName() {
        return new ReservationName(this.reservationName);
    }

    public ReservationDate getReservationDate() {
        return new ReservationDate(this.reservationDate);
    }

    public ReservationTimeId getReservationTimeId() {
        return new ReservationTimeId(this.reservationTimeId);
    }

    public ThemeId getThemeId() {
        return new ThemeId(this.themeId);
    }
}
