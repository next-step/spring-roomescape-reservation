package nextstep.application.themes.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ThemeDeleteValidationDto(Long id) {

}
