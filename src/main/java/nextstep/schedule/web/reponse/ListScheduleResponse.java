package nextstep.schedule.web.reponse;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.theme.domain.Theme;

public class ListScheduleResponse {

    private final Long id;
    private final Theme theme;
    private final LocalDate date;
    private final LocalTime time;

    public ListScheduleResponse(Long id, Theme theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Theme getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
