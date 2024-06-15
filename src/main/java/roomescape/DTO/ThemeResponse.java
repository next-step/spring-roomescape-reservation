package roomescape.DTO;

import roomescape.Entity.Theme;

import java.util.List;

public class ThemeResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final String thumbnail;

    public ThemeResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.description = theme.getDescription();
        this.thumbnail = theme.getThumbnail();
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

    public static List<ThemeResponse> toResponses(List<Theme> themes) {
        return themes.stream().map(ThemeResponse::new).toList();
    }
}
