package roomescape.repository.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationViewProjection {

    private final Long reservationId;

    private final String reservationName;

    private final LocalDate reservationDate;

    private final Long reservationTimeId;

    private final LocalTime reservationTimeStartAt;

    private final String themeName;

    public ReservationViewProjection(
            Long reservationId,
            String reservationName,
            LocalDate reservationDate,
            Long reservationTimeId,
            LocalTime reservationTimeStartAt,
            String themeName
    ) {
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.reservationTimeStartAt = reservationTimeStartAt;
        this.themeName = themeName;
    }

    public Long getReservationId() {
        return reservationId;
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

    public LocalTime getReservationTimeStartAt() {
        return reservationTimeStartAt;
    }

    public String getThemeName() {
        return themeName;
    }
}
