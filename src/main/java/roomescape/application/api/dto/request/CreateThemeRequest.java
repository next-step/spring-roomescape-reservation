package roomescape.application.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import roomescape.application.service.command.CreateThemeCommand;

public class CreateThemeRequest {

    @NotBlank
    private final String name;

    @NotBlank
    private final String description;

    @NotBlank
    private final String thumbnail;

    public CreateThemeRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public CreateThemeCommand toCommand() {
        return new CreateThemeCommand(name, description, thumbnail);
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
