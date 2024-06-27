package roomescape.domain.theme;

import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;
import roomescape.util.validator.ObjectValidator;

public class Theme {

    private final ThemeId id;

    private final ThemeName name;

    private final ThemeDescription description;

    private final ThemeThumbnail thumbnail;

    public Theme(
            ThemeId themeId,
            ThemeName name,
            ThemeDescription description,
            ThemeThumbnail thumbnail
    ) {
        ObjectValidator.validateNotNull(themeId, name, description, thumbnail);
        this.id = themeId;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id.id();
    }

    public String getName() {
        return this.name.name();
    }

    public String getDescription() {
        return this.description.description();
    }

    public String getThumbnail() {
        return this.thumbnail.thumbnail();
    }
}
