package nextstep.domain.schedule.model;

import nextstep.domain.Identity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private Long id;
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    public Schedule(Long id, Long themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Schedule withId() {
        this.id = Identity.getId(Schedule.class);
        return this;
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
