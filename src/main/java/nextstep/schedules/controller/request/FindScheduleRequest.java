package nextstep.schedules.controller.request;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class FindScheduleRequest {

    private final Long themeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    public FindScheduleRequest(Long themeId, LocalDate date) {
        this.themeId = themeId;
        this.date = date;
    }

    public Long getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }
}
