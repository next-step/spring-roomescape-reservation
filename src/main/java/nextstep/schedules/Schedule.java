package nextstep.schedules;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.themes.Themes;

public class Schedule {

    private final Long id;
    private final Themes themes;
    private final LocalDate date;
    private final LocalTime time;

    public Schedule(Long id, Themes themes, LocalDate date, LocalTime time) {
        this.id = id;
        this.themes = themes;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Themes getThemes() {
        return themes;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
