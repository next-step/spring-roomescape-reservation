package roomescape.application.service.command;

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

    public String getReservationName() {
        return reservationName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Long getReservationTimeId() {
        return reservationTimeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
