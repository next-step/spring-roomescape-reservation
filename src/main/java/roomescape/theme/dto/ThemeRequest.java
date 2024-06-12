package roomescape.theme.dto;

import jakarta.validation.constraints.NotBlank;

public class ThemeRequest {

    @NotBlank(message = "테마 이름")
    private String name;

    @NotBlank(message = "테마 설명")
    private String description;

    @NotBlank(message = "테마 썸네일 URL")
    private String thumbnail;

    public ThemeRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
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
