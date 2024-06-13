package roomescape.reservation.domain;

import roomescape.reservation.error.exception.ErrorCode;
import roomescape.reservation.error.exception.ReservationException;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.Time;

public class Reservation {

    private static final String NAME_FORMAT = "^[A-Za-z가-힣\\s]{2,20}$";
    private static final String DATE_FORMAT = "^(?:(?:(?:(?:19|20)[0-9]{2})-(?:(?:0[13578]|1[02])-(?:0[1-9]|[12][0-9]|3[01])|(?:0[469]|11)-(?:0[1-9]|[12][0-9]|30)|02-(?:0[1-9]|1[0-9]|2[0-8])))|(?:((?:19|20)(?:[02468][048]|[13579][26]))-02-29))$";

    private Long id;
    private String name;
    private String date;
    private Time time;
    private Theme theme;

    public Reservation(Long id, String name, String date, Time time, Theme theme) {
        this.id = id;
        checkFormats(name, date);
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    private void checkFormats(String name, String date) {
        if (!name.matches(NAME_FORMAT)) {
            throw new ReservationException(ErrorCode.INVALID_THEME_NAME_FORMAT_ERROR);
        }
        if (!date.matches(DATE_FORMAT)) {
            throw new ReservationException(ErrorCode.INVALID_THEME_DATE_FORMAT_ERROR);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
