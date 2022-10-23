package nextstep.application.schedule.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ScheduleDeleteValidationDto(Long id) {

}
