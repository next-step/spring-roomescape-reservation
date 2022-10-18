package nextstep.app.web.schedule.port.web;

import nextstep.app.web.schedule.application.dto.CreateScheduleCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ScheduleCreateRequest(Long themeId,
                                    String date,
                                    String time) {
    public CreateScheduleCommand toCommand() {
        return new CreateScheduleCommand(
                themeId,
                LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
