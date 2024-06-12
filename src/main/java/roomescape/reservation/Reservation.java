package roomescape.reservation;

import roomescape.reservationTime.ReservationTime;
import roomescape.theme.Theme;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private Long id;

    private String name;

    private LocalDate date;

    private ReservationTime reservationTime;

    private Theme theme;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime,
        Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
        this.theme = theme;
    }

    public Reservation(String name, String date, ReservationTime reservationTime, Theme theme) {
        this(null, name, LocalDate.parse(date), reservationTime, theme);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Theme getTheme() {
        return theme;
    }

    public boolean isBeforeThenNow() {
        return LocalDateTime.of(date, reservationTime.getStartAt()).isBefore(LocalDateTime.now());
    }
}
