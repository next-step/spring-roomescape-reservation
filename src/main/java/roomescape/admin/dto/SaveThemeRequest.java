package roomescape.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SaveThemeRequest(@NotEmpty(message = "테마명은 비어 있을 수 없습니다.")
                               @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "테마명은 문자만 포함할 수 있습니다.")
                               String name,
                               String description,
                               String thumbnail) {
}
