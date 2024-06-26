package roomescape.domain.reservation;

import roomescape.domain.SystemClockHolder;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.validator.CreateReservationValidator;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDate;

public class Reservation {

    private final ReservationId id;

    private final ReservationName name;

    private final ReservationDate date;

    private final ReservationTimeId timeId;

    private final ThemeId themeId;


    public Reservation(
            ReservationId id,
            ReservationName name,
            ReservationDate date,
            ReservationTimeId timeId,
            ThemeId themeId
    ) {
        ObjectValidator.validateNotNull(id, name, date, timeId, themeId);
        this.timeId = timeId;
        this.themeId = themeId;
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public static Reservation create(
            ReservationId id,
            ReservationName name,
            ReservationDate date,
            ReservationTime time,
            Theme theme
    ) {
        CreateReservationValidator.validate(date, time, new SystemClockHolder());

        return new Reservation(
                id,
                name,
                date,
                new ReservationTimeId(time.getId()),
                new ThemeId(theme.getId())
        );
    }

    public Long getId() {
        return id.id();
    }

    public String getName() {
        return name.name();
    }

    public String getFormatDate(String pattern) {
        return this.date.getFormatted(pattern);
    }

    public LocalDate getDate() {
        return this.date.date();
    }

    public Long getReservationTimeId() {
        return timeId.id();
    }

    public Long getThemeId() {
        return this.themeId.id();
    }
}
