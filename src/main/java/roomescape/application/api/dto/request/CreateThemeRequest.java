package roomescape.application.api.dto.request;

import roomescape.application.service.command.CreateThemeCommand;

public class CreateThemeRequest {

    private final String name;

    private final String description;

    private final String thumbnail;

    public CreateThemeRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public CreateThemeCommand toCreateThemeCommand() {
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
