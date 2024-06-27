package roomescape.application.presentation.api.dto.response;

import roomescape.domain.theme.Theme;

public class CreateThemeResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final String thumbnail;

    public CreateThemeResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static CreateThemeResponse from(Theme theme) {
        return new CreateThemeResponse(
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
}
