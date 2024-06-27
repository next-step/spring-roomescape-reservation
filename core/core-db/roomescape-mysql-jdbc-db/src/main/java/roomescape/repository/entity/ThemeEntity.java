package roomescape.repository.entity;

import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;

public class ThemeEntity {

    private final Long id;

    private final String name;

    private final String description;

    private final String thumbnail;

    public ThemeEntity(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static ThemeEntity from(Theme theme) {
        return new ThemeEntity(
                theme.getId(),
                theme.getName(),
                theme.getDescription(),
                theme.getThumbnail()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ThemeEntity withId(Long id) {
        return new ThemeEntity(id, this.name, this.description, this.thumbnail);
    }

    public Theme toDomain() {
        return new Theme(
                new ThemeId(this.id),
                new ThemeName(this.name),
                new ThemeDescription(this.description),
                new ThemeThumbnail(this.thumbnail)
        );
    }
}
