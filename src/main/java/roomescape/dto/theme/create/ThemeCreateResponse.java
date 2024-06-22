package roomescape.dto.theme.create;

import roomescape.domain.Theme;

public class ThemeCreateResponse {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;



    public static ThemeCreateResponse toResponse(Theme theme) {
        return new ThemeCreateResponse(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail());
    }
    public ThemeCreateResponse() {
    }
    public ThemeCreateResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
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
}
