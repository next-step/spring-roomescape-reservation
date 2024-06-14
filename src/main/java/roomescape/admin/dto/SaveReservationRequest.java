package roomescape.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SaveReservationRequest(@NotEmpty(message = "이름은 비어 있을 수 없습니다.")
                                     @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 문자만 포함할 수 있습니다.")
                                     String name,
                                     @NotEmpty(message = "날짜는 비어 있을 수 없습니다.")
                                     @Pattern(regexp = "^[0-9-]+$", message = "날짜에는 숫자와 '-'만 포함될 수 있습니다.")
                                     String date,
                                     Long timeId,
                                     Long themeId) {
}