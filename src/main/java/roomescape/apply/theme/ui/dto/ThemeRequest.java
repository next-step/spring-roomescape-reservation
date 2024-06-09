package roomescape.apply.theme.ui.dto;

import org.springframework.util.StringUtils;

public record ThemeRequest(
        String name,
        String description,
        String thumbnail
) {
    public ThemeRequest {
        validateValues(name, description, thumbnail);
    }

    private static void validateValues(String name, String description, String thumbnail) {
        if (!StringUtils.hasText(name) | !StringUtils.hasText(description) | !StringUtils.hasText(thumbnail)) {
            String message = String.format("필수 값은 비어 있을 수 없습니다. name = %s, description = %s, thumbnail = %s", name, description, thumbnail);
            throw new IllegalArgumentException(message);
        }
    }
}
