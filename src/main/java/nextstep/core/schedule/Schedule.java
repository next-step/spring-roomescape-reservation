package nextstep.core.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private final Long id;
    private final Long themeId;
    private final LocalDate date;
    private final LocalTime time;

    public Schedule(Long themeId, LocalDate date, LocalTime time) {
        this(null, themeId, date, time);
    }

    public Schedule(Long id, Long themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
