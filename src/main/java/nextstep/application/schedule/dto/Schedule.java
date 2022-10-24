package nextstep.application.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Schedule(Long themeId, LocalDate date, LocalTime time) {

}
