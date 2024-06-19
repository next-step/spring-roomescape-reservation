package roomescape.theme.ui.dto;

import roomescape.theme.domain.entity.Theme;

import java.util.List;

public class ThemeResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final String thumbnail;

    private ThemeResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static ThemeResponse from(Theme theme) {
        return new ThemeResponse(
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

    public static List<ThemeResponse> fromThemes(List<Theme> themes) {
        return themes.stream().map(ThemeResponse::from).toList();
    }
}
