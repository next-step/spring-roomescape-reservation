package roomescape.theme.ui.dto;

import jakarta.validation.constraints.NotBlank;

public class ThemeRequest {
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NotBlank
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
