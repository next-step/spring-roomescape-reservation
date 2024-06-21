package roomescape.application.mapper;

import roomescape.domain.theme.Theme;
import roomescape.repository.entity.ThemeEntity;

public final class ThemeEntityMapper {

    private ThemeEntityMapper() {
        throw new UnsupportedOperationException(ThemeEntityMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ThemeEntity toThemeEntity(Theme theme) {
        return new ThemeEntity(
                theme.getId(),
                theme.getThemeName(),
                theme.getThemeDescription(),
                theme.getThemeThumbnail()
        );
    }
}
