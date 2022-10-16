package nextstep.dto;

import nextstep.domain.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleFindAllResponse {
    private List<ScheduleFindResponse> schedules;

    public ScheduleFindAllResponse() {
    }

    private ScheduleFindAllResponse(List<ScheduleFindResponse> schedules) {
        this.schedules = schedules;
    }

    public static ScheduleFindAllResponse from(List<Schedule> schedules) {
        List<ScheduleFindResponse> scheduleFindResponses = schedules.stream()
                .map(ScheduleFindResponse::from)
                .collect(Collectors.toList());
        return new ScheduleFindAllResponse(scheduleFindResponses);
    }

    public List<ScheduleFindResponse> getSchedules() {
        return schedules;
    }
}
