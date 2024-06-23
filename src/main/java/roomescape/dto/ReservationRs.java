package roomescape.dto;

import roomescape.model.ReservationTime;
import roomescape.model.Theme;

public class ReservationRs {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;
    private Theme theme;

    public ReservationRs() {}

    public ReservationRs(Long id, String name, String date, ReservationTime time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setTime(ReservationTime time) {
        this.time = time;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
