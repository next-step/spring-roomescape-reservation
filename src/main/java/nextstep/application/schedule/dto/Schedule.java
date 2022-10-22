package nextstep.application.schedule.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Schedule(Long themeId, LocalDate date, String time) {

}
