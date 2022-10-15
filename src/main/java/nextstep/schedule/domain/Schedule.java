package nextstep.schedule.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule {

    private final Long id;
    private final Long themeId;
    private final LocalDate date;
    private final LocalTime time;

    public Schedule(Long themeId, LocalDate date, LocalTime time) {
        this(null, themeId, date, time);
    }

    public Schedule(Long id, Schedule schedule) {
        this(id, schedule.getThemeId(), schedule.getDate(), schedule.getTime());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
