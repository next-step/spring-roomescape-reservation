package nextstep.domain.schedule.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Schedule(Long id,
                       Long themeId,
                       LocalDate date,
                       LocalTime time) {
    public Schedule withId(Long id) {
        return new Schedule(id, themeId, date, time);
    }
}
