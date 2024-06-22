package roomescape.dto.theme;


import roomescape.domain.Theme;

public class ThemeResponse {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public static ThemeResponse fromEntity(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail());
    }

    public ThemeResponse() {
    }

    public ThemeResponse(Long id, String name, String description, String thumbnail) {
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
