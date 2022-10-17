package nextstep.roomescape.core.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    private final Integer id;
    private final Integer themeId;
    private final LocalDate date;
    private final LocalTime time;

    public Schedule(Integer id, Integer themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Integer id() {
        return id;
    }

    public Integer themeId() {
        return themeId;
    }

    public LocalDate date() {
        return date;
    }

    public LocalTime time() {
        return time;
    }
}
