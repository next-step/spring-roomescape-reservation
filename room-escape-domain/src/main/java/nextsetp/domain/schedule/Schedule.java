package nextsetp.domain.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    public Schedule(Long themeId, LocalDate date, LocalTime time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
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
