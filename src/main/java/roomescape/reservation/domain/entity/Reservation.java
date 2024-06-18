package roomescape.reservation.domain.entity;

import roomescape.reservationtime.domain.entity.ReservationTime;
import roomescape.theme.domain.entity.Theme;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;
    private final Theme theme;

    private Reservation(Long id, String name, String date, ReservationTime time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static Reservation of(Long id, String name, String date, ReservationTime time, Theme theme) {
        return new Reservation(id, name, date, time, theme);
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

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
