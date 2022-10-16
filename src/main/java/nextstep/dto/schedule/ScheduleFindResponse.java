package nextstep.dto.schedule;

import nextstep.domain.Schedule;

public class ScheduleFindResponse {
    private final Long id;
    private final String date;
    private final String time;

    private ScheduleFindResponse(Long id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public static ScheduleFindResponse from(Schedule schedule) {
        return new ScheduleFindResponse(
                schedule.getId(),
                schedule.getDate().toString(),
                schedule.getTime().toString()
        );
    }
}
