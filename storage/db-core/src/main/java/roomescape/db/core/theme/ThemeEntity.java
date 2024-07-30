package roomescape.db.core.theme;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;

@Getter
public class ThemeEntity {

    private final Long themeId;
    private final String name;
    private final String description;
    private final String thumbnail;

    @Builder
    private ThemeEntity(
            final Long themeId,
            final String name,
            final String description,
            final String thumbnail
    ) {
        this.themeId = themeId;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static ThemeEntity fromModel(final Theme theme) {
        return ThemeEntity.builder()
                .themeId(theme.getIdValue().orElse(null))
                .name(theme.getName())
                .description(theme.getDescription())
                .thumbnail(theme.getThumbnail())
                .build();
    }

    public Theme toModel() {
        return Theme.builder()
                .id(new ThemeId(this.themeId))
                .name(this.name)
                .description(this.description)
                .thumbnail(this.thumbnail)
                .build();
    }
}
