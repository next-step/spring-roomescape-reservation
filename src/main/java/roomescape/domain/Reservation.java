package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;

    private ReservationTime time;

    private Theme theme;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, Long timeId, String startAt, Long themeId, String themeName) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = new ReservationTime(timeId, startAt);
        this.theme = new Theme(themeId, themeName);
    }

    public Reservation(Long id, String name, String date, ReservationTime time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = new ReservationTime(time.getId(), time.getStartAt());
        this.theme = new Theme(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail());
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

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
