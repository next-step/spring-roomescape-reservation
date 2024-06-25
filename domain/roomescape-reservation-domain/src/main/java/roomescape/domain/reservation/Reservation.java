package roomescape.domain.reservation;

import roomescape.domain.reservation.service.CreateReservationValidator;
import roomescape.domain.reservation.service.SystemClockHolder;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDate;

public class Reservation {

    private final ReservationId id;

    private final ReservationName name;

    private final ReservationDate date;

    private final ReservationTime time;

    private final Theme theme;


    public Reservation(
            ReservationId id,
            ReservationName name,
            ReservationDate date,
            ReservationTime time,
            Theme theme
    ) {
        ObjectValidator.validateNotNull(id, name, date, time, theme);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static Reservation create(
            ReservationId id,
            ReservationName name,
            ReservationDate date,
            ReservationTime time,
            Theme theme
    ) {
        CreateReservationValidator validator = new CreateReservationValidator(date, time, new SystemClockHolder());
        validator.validate();

        return new Reservation(id, name, date, time, theme);
    }

    public Long getId() {
        return id.id();
    }

    public String getName() {
        return name.name();
    }

    public String getFormattedReservationDate(String pattern) {
        return this.date.getFormatted(pattern);
    }

    public LocalDate getDate() {
        return this.date.date();
    }

    public Long getReservationTimeId() {
        return time.getId();
    }

    public Long getThemeId() {
        return this.theme.getId();
    }
}
