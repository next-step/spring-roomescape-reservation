package roomescape.application.service.command;

import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;

public class CreateThemeCommand {

    private final String name;

    private final String description;

    private final String thumbnail;

    public CreateThemeCommand(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Theme toTheme() {
        return new Theme(
                ThemeId.empty(),
                new ThemeName(this.name),
                new ThemeDescription(this.description),
                new ThemeThumbnail(this.thumbnail)
        );
    }
}
