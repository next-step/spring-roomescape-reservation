package roomescape.theme.ui.dto;

public class ThemeRequest {
    private final String name;
    private final String description;
    private final String thumbnail;

    private ThemeRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static ThemeRequest create(String name, String description, String thumbnail) {
        return new ThemeRequest(name, description, thumbnail);
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
