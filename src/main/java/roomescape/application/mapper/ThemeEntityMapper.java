package roomescape.application.mapper;

import roomescape.domain.theme.Theme;
import roomescape.repository.entity.ThemeEntity;

public abstract class ThemeEntityMapper {

    public static ThemeEntity toThemeEntity(Theme theme) {
        return new ThemeEntity(
                theme.getId(),
                theme.getThemeName(),
                theme.getThemeDescription(),
                theme.getThemeThumbnail()
        );
    }
}
