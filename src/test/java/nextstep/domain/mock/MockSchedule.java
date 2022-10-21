package nextstep.domain.mock;

import nextstep.domain.schedule.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class MockSchedule {
    private final Long id;
    private final Long themeId;
    private final LocalDate date;
    private final LocalTime time;

    private MockSchedule(Long id, Long themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public static Schedule from() {
        return new Schedule(
                1L,
                1L,
                LocalDate.of(2022, 8, 11),
                LocalTime.of(11, 11)
        );
    }
}
