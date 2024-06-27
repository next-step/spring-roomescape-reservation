package roomescape.domain.theme;

import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;
import roomescape.util.validator.ObjectValidator;

public class Theme {

    private final ThemeId themeId;

    private final ThemeName themeName;

    private final ThemeDescription themeDescription;

    private final ThemeThumbnail themeThumbnail;

    public Theme(ThemeId themeId, ThemeName themeName, ThemeDescription themeDescription, ThemeThumbnail themeThumbnail) {
        ObjectValidator.validateNotNull(themeId, themeName, themeDescription, themeThumbnail);
        this.themeId = themeId;
        this.themeName = themeName;
        this.themeDescription = themeDescription;
        this.themeThumbnail = themeThumbnail;
    }

    public Long getId() {
        return themeId.id();
    }

    public String getThemeName() {
        return this.themeName.name();
    }

    public String getThemeDescription() {
        return this.themeDescription.description();
    }

    public String getThemeThumbnail() {
        return this.themeThumbnail.thumbnail();
    }
}
