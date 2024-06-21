package roomescape.application.mapper;

import roomescape.domain.theme.Theme;
import roomescape.domain.theme.Themes;
import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;
import roomescape.repository.entity.ThemeEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ThemeMapper {

    private ThemeMapper() {
        throw new UnsupportedOperationException(ThemeMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static Theme toTheme(ThemeEntity themeEntity) {
        return new Theme(
                new ThemeId(themeEntity.getId()),
                new ThemeName(themeEntity.getName()),
                new ThemeDescription(themeEntity.getDescription()),
                new ThemeThumbnail(themeEntity.getThumbnail())
        );
    }

    public static Themes toThemes(List<ThemeEntity> themeEntities) {
        return new Themes(
                themeEntities.stream()
                        .map(ThemeMapper::toTheme)
                        .collect(Collectors.toList())
        );
    }
}
