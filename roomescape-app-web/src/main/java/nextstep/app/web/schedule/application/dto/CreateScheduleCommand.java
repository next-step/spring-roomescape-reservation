package nextstep.app.web.schedule.application.dto;

import java.time.LocalDateTime;

public record CreateScheduleCommand(Long themeId,
                                    LocalDateTime dateTime) {
}