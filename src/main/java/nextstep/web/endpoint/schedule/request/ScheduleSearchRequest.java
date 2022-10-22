package nextstep.web.endpoint.schedule.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@ToString
public class ScheduleSearchRequest {
    private final Long themeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    public ScheduleSearchRequest(Long themeId, LocalDate date) {
        this.themeId = themeId;
        this.date = date;
    }
}
