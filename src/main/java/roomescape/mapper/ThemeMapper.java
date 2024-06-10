package roomescape.mapper;

import roomescape.domain.Theme;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;

public class ThemeMapper {

    public static ThemeResponse toDto(Theme theme) {
        if (theme == null) {
            return null;
        }

        return ThemeResponse.fromEntity(theme);
    }

    public static Theme toEntity(ThemeCreateRequest request) {
        if (request == null) {
            return  null;
        }
        return new Theme(request.getName(), request.getDescription(), request.getThumbnail());
    }
}
